package com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginForm {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(min = 8)
    private String password;
}