package com.kingposhwolf.com.customerregistrationagentsapi.controllers;

import com.kingposhwolf.com.customerregistrationagentsapi.common.controllers.BaseController;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.ward.CreateWardDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.ward.WardListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Ward;
import com.kingposhwolf.com.customerregistrationagentsapi.services.WardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wards")
public class WardController extends BaseController<Ward, Long, CreateWardDto, CreateWardDto, WardListDto, WardListDto, WardService> {
    public WardController(WardService service) {
        super(service);
    }
}
