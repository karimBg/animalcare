package com.veiter.animalcare.services.impl;

import com.veiter.animalcare.Exception.MyException;
import com.veiter.animalcare.enums.OrderStatusEnum;
import com.veiter.animalcare.enums.ResultEnum;
import com.veiter.animalcare.models.AnimalInOrder;
import com.veiter.animalcare.models.AnimalInfo;
import com.veiter.animalcare.models.OrderMain;
import com.veiter.animalcare.repositories.AnimalInOrderRepository;
import com.veiter.animalcare.repositories.AnimalInfoRepository;
import com.veiter.animalcare.repositories.OrderRepository;
import com.veiter.animalcare.repositories.UserRepository;
import com.veiter.animalcare.services.AnimalService;
import com.veiter.animalcare.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnimalInfoRepository animalInfoRepository;
    @Autowired
    AnimalService animalService;
    @Autowired
    AnimalInOrderRepository animalInOrderRepository;

    @Override
    public Page<OrderMain> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }

    @Override
    public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
        return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
    }

    @Override
    public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }

    @Override
    public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
        return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
    }

    @Override
    public OrderMain findOne(Long orderId) {
        OrderMain orderMain = orderRepository.findByOrderId(orderId);
        if(orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    @Override
    @Transactional
    public OrderMain finish(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public OrderMain cancel(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // Restore Stock
        Iterable<AnimalInOrder> animals = orderMain.getAnimals();
        for(AnimalInOrder animalInOrder : animals) {
            AnimalInfo animalInfo = animalInfoRepository.findByAnimalId(animalInOrder.getAnimalId());
            if(animalInfo != null) {
                animalService.increaseStock(animalInOrder.getAnimalId(), animalInOrder.getCount());
            }
        }
        return orderRepository.findByOrderId(orderId);

    }
}
