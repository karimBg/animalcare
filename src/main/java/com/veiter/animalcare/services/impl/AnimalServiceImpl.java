package com.veiter.animalcare.services.impl;

import com.veiter.animalcare.Exception.MyException;
import com.veiter.animalcare.enums.AnimalSaleStatusEnum;
import com.veiter.animalcare.enums.ResultEnum;
import com.veiter.animalcare.models.AnimalInfo;
import com.veiter.animalcare.repositories.AnimalInfoRepository;
import com.veiter.animalcare.services.AnimalService;
import com.veiter.animalcare.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    AnimalInfoRepository animalInfoRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public AnimalInfo findOne(String animalId) {

        AnimalInfo animalInfo = animalInfoRepository.findByAnimalId(animalId);
        return animalInfo;
    }

    @Override
    public Page<AnimalInfo> findUpAll(Pageable pageable) {
        return animalInfoRepository.findAllByAnimalSaleStatusOrderByAnimalIdAsc(AnimalSaleStatusEnum.UP.getCode(),pageable);
    }

    @Override
    public Page<AnimalInfo> findAll(Pageable pageable) {
        return animalInfoRepository.findAllByOrderByAnimalId(pageable);
    }

    @Override
    public Page<AnimalInfo> findAllInCategory(Integer categoryType, Pageable pageable) {
        return animalInfoRepository.findAllByAnimalTypeOrderByAnimalIdAsc(categoryType, pageable);
    }

    @Override
    @Transactional
    public void increaseStock(String animalId, int amount) {
        AnimalInfo animalInfo = findOne(animalId);
        if (animalInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = animalInfo.getAnimalStock() + amount;
        animalInfo.setAnimalStock(update);
        animalInfoRepository.save(animalInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(String animalId, int amount) {
        AnimalInfo animalInfo = findOne(animalId);
        if (animalInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = animalInfo.getAnimalStock() - amount;
        if(update <= 0) throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH );

        animalInfo.setAnimalStock(update);
        animalInfoRepository.save(animalInfo);
    }

    @Override
    @Transactional
    public AnimalInfo offSale(String animalId) {
        AnimalInfo animalInfo = findOne(animalId);
        if (animalInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        if (animalInfo.getAnimalSaleStatus() == AnimalSaleStatusEnum.DOWN.getCode()) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        animalInfo.setAnimalSaleStatus(AnimalSaleStatusEnum.DOWN.getCode());
        return animalInfoRepository.save(animalInfo);
    }

    @Override
    @Transactional
    public AnimalInfo onSale(String animalId) {
        AnimalInfo animalInfo = findOne(animalId);
        if (animalInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        if (animalInfo.getAnimalSaleStatus() == AnimalSaleStatusEnum.UP.getCode()) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        animalInfo.setAnimalSaleStatus(AnimalSaleStatusEnum.UP.getCode());
        return animalInfoRepository.save(animalInfo);
    }

    @Override
    public AnimalInfo update(AnimalInfo animalInfo) {

        // if null throw exception
        categoryService.findByCategoryType(animalInfo.getAnimalType());
        if(animalInfo.getAnimalSaleStatus() > 1) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }


        return animalInfoRepository.save(animalInfo);
    }

    @Override
    public AnimalInfo save(AnimalInfo animalInfo) {
        return update(animalInfo);
    }

    @Override
    public void delete(String animalId) {
        AnimalInfo animalInfo = findOne(animalId);
        if (animalInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
        animalInfoRepository.delete(animalInfo);

    }

}
