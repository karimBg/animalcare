package com.veiter.animalcare.security.request;

import lombok.Data;

@Data
public class LoginForm {

    private String username;
    private String password;
}
