package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.common.services.BaseService;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.location.ward.CreateWardDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.ward.WardListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Ward;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.ward.WardRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class WardService extends BaseService<Ward, Long, CreateWardDto, CreateWardDto, WardListDto, WardListDto>{
    public WardService(WardRepositoryImpl repository) {
        super(repository, "Ward");
    }

    @Override
    protected Ward mapCreateDTOToEntity(CreateWardDto createWardDto) {
        return null;
    }

    @Override
    protected void mapUpdateDTOToEntity(CreateWardDto createWardDto, Ward entity) {

    }

    @Override
    protected WardListDto mapEntityToListDTO(Ward entity) {
        return entity.toWardListDto();
    }

    @Override
    protected WardListDto mapEntityToDetailDTO(Ward entity) {
        return entity.toWardListDto();
    }
}
