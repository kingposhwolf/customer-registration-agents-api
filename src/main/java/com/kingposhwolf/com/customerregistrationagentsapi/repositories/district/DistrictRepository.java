package com.kingposhwolf.com.customerregistrationagentsapi.repositories.district;

import com.kingposhwolf.com.customerregistrationagentsapi.models.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {
    Optional<District> findByName(String name);
}
