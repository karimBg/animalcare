package com.veiter.animalcare.services;

import com.veiter.animalcare.models.AnimalInOrder;
import com.veiter.animalcare.models.Cart;
import com.veiter.animalcare.models.User;

import java.util.Collection;

public interface CartService {

    Cart getCart(User user);

    void mergeLocalCart(Collection<AnimalInOrder> animalInOrders, User user);

    void delete(String itemId, User user);

    void checkout(User user);

}
