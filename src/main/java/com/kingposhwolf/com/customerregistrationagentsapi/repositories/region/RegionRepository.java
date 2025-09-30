package com.kingposhwolf.com.customerregistrationagentsapi.repositories.region;

import com.kingposhwolf.com.customerregistrationagentsapi.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {
    Optional<Region> findByName(String name);
}