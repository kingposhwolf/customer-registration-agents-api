package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.district;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DistrictListDto {
    private Long id;
    private String name;
}
