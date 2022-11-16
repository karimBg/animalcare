package com.veiter.animalcare.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class AnimalInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderMain orderMain;

    private String animalId;

    private String animalName;

    @NotNull
    private String animalDescription;

    private String animalIcon;

    @NotNull
    private Integer categoryType;

    @NotNull
    private BigDecimal animalPrice;

    private Integer animalStock;

    private Integer count;

    public AnimalInOrder(AnimalInfo animalInfo, Integer quantity) {
        this.animalId = animalInfo.getAnimalId();
        this.animalName = animalInfo.getAnimalName();
        this.animalDescription = animalInfo.getAnimalDescription();
        this.animalIcon = animalInfo.getAnimalIcon();
        this.categoryType = animalInfo.getAnimalType();
        this.animalPrice = animalInfo.getAnimalPrice();
        this.animalStock = animalInfo.getAnimalStock();
        this.count = quantity;
    }

}
