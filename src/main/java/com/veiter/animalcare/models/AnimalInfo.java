package com.veiter.animalcare.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DynamicUpdate
public class AnimalInfo {

    @Id
    private String animalId;

    @NotNull
    private String animalName;

    @NotNull
    private BigDecimal animalPrice;

    private String animalDescription;

    private String animalIcon;

    /** 0: on-sale 1: off-sale */
    @ColumnDefault("0")
    private Integer animalSaleStatus;

    @ColumnDefault("0")
    private Integer animalType;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

}
