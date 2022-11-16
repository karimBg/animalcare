package com.veiter.animalcare.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DynamicUpdate
public class AnimalCategory {

    @Id
    @GeneratedValue
    private Integer categoryId;
    private String categoryName;

    private Date creatTime;
    private Date updateTime;
}
