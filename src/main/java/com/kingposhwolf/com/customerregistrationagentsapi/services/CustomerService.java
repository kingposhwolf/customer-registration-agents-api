package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.common.dto.QueryParamsDTO;
import com.kingposhwolf.com.customerregistrationagentsapi.common.services.BaseService;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.customer.CreateCustomerDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.customer.CustomerListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.UserDetailsDto.Location;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.UserDetailsDto.LocationValue;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.paginated.PaginatedResponse;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Customer;
import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.customer.CustomerRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CustomerService extends BaseService<Customer, Long, CreateCustomerDto, CreateCustomerDto, CustomerListDto, CustomerListDto> {

    private final WardService wardService;
    private final CustomerRepositoryCustom customerRepositoryImpl;

    public CustomerService(CustomerRepositoryCustom repository, WardService wardService, CustomerRepositoryCustom customerRepositoryImpl) {
        super(repository, "Customer");
        this.wardService = wardService;
        this.customerRepositoryImpl = customerRepositoryImpl;
    }

    public PaginatedResponse<CustomerListDto> findByRegisteredBy(
            User user,
            QueryParamsDTO queryParams) {

        Map<String, Object> filters = Map.of("registeredBy", user.getId());
        return findAllAsListDTO(queryParams, filters);
    }

    @Override
    protected Customer mapCreateDTOToEntity(CreateCustomerDto createCustomerDto) {
        log.warn("reach here =======>");
        Customer customer = new Customer();
        customer.setName(createCustomerDto.getName());
        customer.setNida(createCustomerDto.getNida());
        customer.setDob(createCustomerDto.getDob());

        if (createCustomerDto.getWardId() != null) {
            customer.setWard(wardService.findOne(createCustomerDto.getWardId()));
        }

        return customer;
    }

    @Override
    protected void mapUpdateDTOToEntity(CreateCustomerDto updateDto, Customer entity) {
        if (updateDto.getName() != null) {
            entity.setName(updateDto.getName());
        }
        if (updateDto.getNida() != null) {
            entity.setNida(updateDto.getNida());
        }
        if (updateDto.getDob() != null) {
            entity.setDob(updateDto.getDob());
        }
        if (updateDto.getWardId() != null) {
            entity.setWard(wardService.findOne(updateDto.getWardId()));
        }
    }

    @Override
    protected CustomerListDto mapEntityToListDTO(Customer entity) {
        return mapToCustomerListDto(entity);
    }

    @Override
    protected CustomerListDto mapEntityToDetailDTO(Customer entity) {
        return mapToCustomerListDto(entity);
    }

    private CustomerListDto mapToCustomerListDto(Customer entity) {
        Location location = null;

        if (entity.getWard() != null) {
            LocationValue wardValue = LocationValue.builder()
                    .id(entity.getWard().getId())
                    .name(entity.getWard().getName())
                    .build();

            LocationValue districtValue = null;
            LocationValue regionValue = null;

            if (entity.getWard().getDistrict() != null) {
                districtValue = LocationValue.builder()
                        .id(entity.getWard().getDistrict().getId())
                        .name(entity.getWard().getDistrict().getName())
                        .build();

                if (entity.getWard().getDistrict().getRegion() != null) {
                    regionValue = LocationValue.builder()
                            .id(entity.getWard().getDistrict().getRegion().getId())
                            .name(entity.getWard().getDistrict().getRegion().getName())
                            .build();
                }
            }

            location = Location.builder()
                    .region(regionValue)
                    .district(districtValue)
                    .ward(wardValue)
                    .build();
        }

        return CustomerListDto.builder()
                .name(entity.getName())
                .nida(entity.getNida())
                .dob(entity.getDob())
                .location(location)
                .build();
    }
}