package com.veiter.animalcare.services;

import com.veiter.animalcare.models.AnimalInOrder;
import com.veiter.animalcare.models.User;

public interface AnimalInOrderService {
    void update(String itemId, Integer quantity, User user);

    AnimalInOrder findOne(String itemId, User user);
}
