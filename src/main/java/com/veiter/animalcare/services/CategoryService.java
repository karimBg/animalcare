package com.veiter.animalcare.services;

import com.veiter.animalcare.models.AnimalCategory;

import java.util.List;

public interface CategoryService {

    List<AnimalCategory> findAll();

    AnimalCategory findByCategoryType(Integer categoryType);

    List<AnimalCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    AnimalCategory save(AnimalCategory productCategory);


}
