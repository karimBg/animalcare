package com.veiter.animalcare.services;

import com.veiter.animalcare.models.AnimalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalService {
    AnimalInfo findOne(String productId);

    // All selling products
    Page<AnimalInfo> findUpAll(Pageable pageable);
    // All products
    Page<AnimalInfo> findAll(Pageable pageable);
    // All products in a category
    Page<AnimalInfo> findAllInCategory(Integer categoryType, Pageable pageable);

    // increase stock
    void increaseStock(String productId, int amount);

    //decrease stock
    void decreaseStock(String productId, int amount);

    AnimalInfo offSale(String productId);

    AnimalInfo onSale(String productId);

    AnimalInfo update(AnimalInfo productInfo);
    AnimalInfo save(AnimalInfo productInfo);

    void delete(String productId);
}
