package com.kingposhwolf.com.customerregistrationagentsapi.repositories.district;

import com.kingposhwolf.com.customerregistrationagentsapi.common.repositories.BaseRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.models.District;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DistrictRepositoryCustom extends BaseRepository<District, Long> {

    public DistrictRepositoryCustom(DistrictRepository jpaRepository) {
        super(
                jpaRepository,
                List.of("name"),
                Map.of()
        );
    }
}
