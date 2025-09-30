package com.kingposhwolf.com.customerregistrationagentsapi.repositories.ward;

import com.kingposhwolf.com.customerregistrationagentsapi.common.repositories.BaseRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Ward;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WardRepositoryCustom extends BaseRepository<Ward, Long> {

    public WardRepositoryCustom(WardRepository jpaRepository) {
        super(
                jpaRepository,
                List.of("name"),
                Map.of()
        );
    }
}
