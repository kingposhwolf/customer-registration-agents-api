package com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.region;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateRegionDto {
    @NotBlank
    private String name;
}
