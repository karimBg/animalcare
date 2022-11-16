package com.veiter.animalcare.services.impl;

import com.veiter.animalcare.Exception.MyException;
import com.veiter.animalcare.enums.ResultEnum;
import com.veiter.animalcare.models.AnimalCategory;
import com.veiter.animalcare.repositories.AnimalCategoryRepository;
import com.veiter.animalcare.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    AnimalCategoryRepository animalCategoryRepository;

    @Override
    public List<AnimalCategory> findAll() {
        List<AnimalCategory> res = animalCategoryRepository.findAllByOrderByCategoryType();
        //  res.sort(Comparator.comparing(AnimalCategory::getCategoryType));
        return res;
    }

    @Override
    public AnimalCategory findByCategoryType(Integer categoryType) {
        AnimalCategory res = animalCategoryRepository.findByCategoryType(categoryType);
        if(res == null) throw new MyException(ResultEnum.CATEGORY_NOT_FOUND);
        return res;
    }

    @Override
    public List<AnimalCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        List<AnimalCategory> res = animalCategoryRepository
                .findByCategoryTypeInOrderByCategoryTypeAsc(categoryTypeList);
        //res.sort(Comparator.comparing(AnimalCategory::getCategoryType));
        return res;
    }

    @Override
    @Transactional
    public AnimalCategory save(AnimalCategory animalCategory) {
        return animalCategoryRepository.save(animalCategory);
    }
}
