package com.veiter.animalcare.services.impl;

import com.veiter.animalcare.Exception.MyException;
import com.veiter.animalcare.enums.ResultEnum;
import com.veiter.animalcare.models.AnimalInOrder;
import com.veiter.animalcare.models.Cart;
import com.veiter.animalcare.models.OrderMain;
import com.veiter.animalcare.models.User;
import com.veiter.animalcare.repositories.AnimalInOrderRepository;
import com.veiter.animalcare.repositories.CartRepository;
import com.veiter.animalcare.repositories.OrderRepository;
import com.veiter.animalcare.repositories.UserRepository;
import com.veiter.animalcare.services.AnimalService;
import com.veiter.animalcare.services.CartService;
import com.veiter.animalcare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    AnimalService animalService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnimalInOrderRepository animalInOrderRepository;

    @Autowired
    CartRepository cartRepository;


    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Transactional
    @Override
    public void mergeLocalCart(Collection<AnimalInOrder> animalInOrders, User user) {
        Cart finalCart = user.getCart();
        animalInOrders.forEach(animalInOrder -> {
            Set<AnimalInOrder> set = finalCart.getAnimalInOrder();
            Optional<AnimalInOrder> old =
                    set.stream()
                    .filter(e -> e.getAnimalId().equals(animalInOrder.getAnimalId()))
                    .findFirst();
            AnimalInOrder animal;
            if(old.isPresent()) {
                animal = old.get();
                animal.setCount(animalInOrder.getCount() + animal.getCount());
            } else {
                animal = animalInOrder;
                animal.setCart(finalCart);
                finalCart.getAnimalInOrder().add(animal);
            }
            animalInOrderRepository.save(animal);
        });
        cartRepository.save(finalCart);
    }

    @Transactional
    @Override
    public void delete(String itemId, User user) {
        if(itemId.equals("") || user == null) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        var op = user.getCart()
                .getAnimalInOrder()
                .stream()
                .filter(e -> itemId.equals(e.getAnimalId())).findFirst();
        op.ifPresent(animalInOrder -> {
            animalInOrder.setCart(null);
            animalInOrderRepository.deleteById(animalInOrder.getId());
        });
    }

    @Transactional
    @Override
    public void checkout(User user) {
        // Creat an order
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key & decrease stock
        user.getCart().getAnimalInOrder().forEach(animalInOrder -> {
            animalInOrder.setCart(null);
            animalInOrder.setOrderMain(order);
            animalService.decreaseStock(animalInOrder.getAnimalId(), animalInOrder.getCount());
            animalInOrderRepository.save(animalInOrder);
        });

    }
}
