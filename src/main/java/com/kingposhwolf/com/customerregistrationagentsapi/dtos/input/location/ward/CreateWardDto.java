package com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.ward;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateWardDto {
    @NotBlank
    private String name;

    @NotNull
    private Long districtId;
}
