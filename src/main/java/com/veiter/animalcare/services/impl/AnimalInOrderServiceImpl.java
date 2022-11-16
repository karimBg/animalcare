package com.veiter.animalcare.services.impl;

import com.veiter.animalcare.models.AnimalInOrder;
import com.veiter.animalcare.models.User;
import com.veiter.animalcare.repositories.AnimalInOrderRepository;
import com.veiter.animalcare.services.AnimalInOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicReference;

public class AnimalInOrderServiceImpl implements AnimalInOrderService {

    @Autowired
    AnimalInOrderRepository animalInOrderRepository;

    @Override
    public void update(String itemId, Integer quantity, User user) {
        var op = user.getCart()
                .getAnimalInOrder()
                .stream()
                .filter(e -> itemId.equals(e.getAnimalId())).findFirst();
        op.ifPresent(animalInOrder -> {
            animalInOrder.setCount(quantity);
            animalInOrderRepository.save(animalInOrder);
        });
    }

    @Override
    public AnimalInOrder findOne(String itemId, User user) {
        var op = user.getCart()
                .getAnimalInOrder()
                .stream()
                .filter(e -> itemId.equals(e.getAnimalId())).findFirst();
        AtomicReference<AnimalInOrder> res = new AtomicReference<>();
        op.ifPresent(res::set);
        return res.get();
    }
}
