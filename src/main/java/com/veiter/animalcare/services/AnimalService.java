package com.veiter.animalcare.services;

import com.veiter.animalcare.models.AnimalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalService {
    AnimalInfo findOne(String animalId);

    // All selling animals
    Page<AnimalInfo> findUpAll(Pageable pageable);
    // All animals
    Page<AnimalInfo> findAll(Pageable pageable);
    // All animals in a category
    Page<AnimalInfo> findAllInCategory(Integer categoryType, Pageable pageable);

    // increase stock
    void increaseStock(String animalId, int amount);

    //decrease stock
    void decreaseStock(String animalId, int amount);

    AnimalInfo offSale(String animalId);

    AnimalInfo onSale(String animalId);

    AnimalInfo update(AnimalInfo animalInfo);
    AnimalInfo save(AnimalInfo animalInfo);

    void delete(String animalId);
}
