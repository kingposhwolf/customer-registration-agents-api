package com.kingposhwolf.com.customerregistrationagentsapi.controllers;

import com.kingposhwolf.com.customerregistrationagentsapi.common.controllers.BaseController;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.district.CreateDistrictDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.district.DistrictListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.District;
import com.kingposhwolf.com.customerregistrationagentsapi.services.DistrictService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/districts")
public class DistrictController extends BaseController<District, Long, CreateDistrictDto, CreateDistrictDto, DistrictListDto, DistrictListDto, DistrictService> {
    public DistrictController(DistrictService service) {
        super(service);
    }
}
