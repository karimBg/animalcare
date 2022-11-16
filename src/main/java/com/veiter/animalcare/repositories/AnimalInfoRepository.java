package com.veiter.animalcare.repositories;

import com.veiter.animalcare.models.AnimalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalInfoRepository extends JpaRepository<AnimalInfo, String> {

    AnimalInfo findByAnimalId(String id);

    // on sale animal
    Page<AnimalInfo> findAllByAnimalSaleStatusOrderByAnimalIdAsc(Integer animalStatus, Pageable pageable);

    // animal in one category
    Page<AnimalInfo> findAllByAnimalTypeOrderByAnimalIdAsc(Integer categoryType, Pageable pageable);

    Page<AnimalInfo> findAllByOrderByAnimalId(Pageable pageable);

}

