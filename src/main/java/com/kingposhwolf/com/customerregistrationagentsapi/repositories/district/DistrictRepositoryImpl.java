package com.kingposhwolf.com.customerregistrationagentsapi.repositories.district;

import com.kingposhwolf.com.customerregistrationagentsapi.common.repositories.BaseRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.models.District;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DistrictRepositoryImpl extends BaseRepository<District, Long> {

    public DistrictRepositoryImpl(@Lazy DistrictRepository jpaRepository) {
        super(
                jpaRepository,
                List.of("name"),
                Map.of()
        );
    }
}
