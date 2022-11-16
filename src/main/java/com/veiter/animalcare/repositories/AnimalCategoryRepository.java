package com.veiter.animalcare.repositories;

import com.veiter.animalcare.models.AnimalCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalCategoryRepository extends JpaRepository<AnimalCategory, Integer> {

    // Some category
    List<AnimalCategory> findByCategoryTypeInOrderByCategoryTypeAsc(List<Integer> animalTypes);
    // All category
    List<AnimalCategory> findAllByOrderByCategoryType();
    // One category
    AnimalCategory findByCategoryType(Integer animalType);
}
