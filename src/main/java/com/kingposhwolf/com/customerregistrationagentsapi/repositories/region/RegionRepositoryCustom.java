package com.kingposhwolf.com.customerregistrationagentsapi.repositories.region;

import com.kingposhwolf.com.customerregistrationagentsapi.common.repositories.BaseRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Region;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RegionRepositoryCustom extends BaseRepository<Region, Long> {

    public RegionRepositoryCustom(RegionRepository jpaRepository) {
        super(
                jpaRepository,
                List.of("name"),
                Map.of()
        );
    }
}
