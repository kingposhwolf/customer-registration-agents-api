package com.kingposhwolf.com.customerregistrationagentsapi.controllers;

import com.kingposhwolf.com.customerregistrationagentsapi.common.controllers.BaseController;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.region.CreateRegionDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.regions.RegainDetailDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.regions.RegionListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Region;
import com.kingposhwolf.com.customerregistrationagentsapi.services.RegionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regions")
public class RegionController extends BaseController<Region, Long, CreateRegionDto, CreateRegionDto, RegionListDto, RegainDetailDto, RegionService> {
    public RegionController(RegionService service) {
        super(service);
    }
}
