package com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.district;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateDistrictDto {
    @NotBlank
    private String name;

    private Long regionId;
}
