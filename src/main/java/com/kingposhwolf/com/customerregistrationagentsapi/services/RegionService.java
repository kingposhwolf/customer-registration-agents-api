package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.common.services.BaseService;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.region.CreateRegionDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.regions.RegainDetailDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.regions.RegionListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Region;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.region.RegionRepositoryCustom;
import com.kingposhwolf.com.customerregistrationagentsapi.util.APIResponder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService extends BaseService<Region, Long, CreateRegionDto, CreateRegionDto, RegionListDto, RegainDetailDto> {

    public RegionService(RegionRepositoryCustom repository) {
        super(repository, "Region");
    }

    @Override
    protected Region mapCreateDTOToEntity(CreateRegionDto createRegionDto) {
        return null;
    }

    @Override
    protected void mapUpdateDTOToEntity(CreateRegionDto createRegionDto, Region entity) {

    }

    @Override
    protected RegionListDto mapEntityToListDTO(Region entity) {
        return entity.toRegionList();
    }

    @Override
    protected RegainDetailDto mapEntityToDetailDTO(Region entity) {
        return RegainDetailDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}