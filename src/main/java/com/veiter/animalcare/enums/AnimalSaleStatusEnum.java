package com.veiter.animalcare.enums;

import lombok.Getter;

@Getter
public enum AnimalSaleStatusEnum implements CodeEnum{

    UP(0, "Available"),
    DOWN(1, "Unavailable");

    private Integer code;
    private String message;

    AnimalSaleStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getStatus(Integer code) {

        for(AnimalSaleStatusEnum statusEnum : AnimalSaleStatusEnum.values()) {
            if(statusEnum.getCode() == code) return statusEnum.getMessage();
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
