package com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.customer;

import com.kingposhwolf.com.customerregistrationagentsapi.annotations.UniqueNida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;

import java.util.Date;

@Getter
public class CreateCustomerDto {
    @NotBlank
    private String name;

    @Past
    private Date dob;

    @NotNull
    private Long wardId;

    @NotBlank
    @UniqueNida
    private String nida;
}
