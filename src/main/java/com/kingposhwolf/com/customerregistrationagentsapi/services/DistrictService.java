package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.common.services.BaseService;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.district.CreateDistrictDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.district.DistrictListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.District;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.district.DistrictRepositoryCustom;
import org.springframework.stereotype.Service;

@Service
public class DistrictService extends BaseService<District, Long, CreateDistrictDto, CreateDistrictDto, DistrictListDto, DistrictListDto> {

    public DistrictService(DistrictRepositoryCustom repository) {
        super(repository, "District");
    }

    @Override
    protected District mapCreateDTOToEntity(CreateDistrictDto createDistrictDto) {
        return null;
    }

    @Override
    protected void mapUpdateDTOToEntity(CreateDistrictDto createDistrictDto, District entity) {

    }

    @Override
    protected DistrictListDto mapEntityToListDTO(District entity) {
        return entity.toDistrictList();
    }

    @Override
    protected DistrictListDto mapEntityToDetailDTO(District entity) {
        return entity.toDistrictList();
    }
}
