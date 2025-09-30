package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.customer;

import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.UserDetailsDto.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerListDto {
    private String name;
    private String nida;
    private Date dob;
    private Location location;
}
