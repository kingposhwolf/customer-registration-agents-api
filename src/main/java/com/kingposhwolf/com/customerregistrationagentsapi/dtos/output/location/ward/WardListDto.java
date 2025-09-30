package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.ward;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WardListDto {
    private Long id;
    private String name;
}
