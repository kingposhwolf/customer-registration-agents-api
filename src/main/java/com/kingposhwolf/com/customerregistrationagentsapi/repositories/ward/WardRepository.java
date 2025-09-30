package com.kingposhwolf.com.customerregistrationagentsapi.repositories.ward;

import com.kingposhwolf.com.customerregistrationagentsapi.models.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward, Long>, JpaSpecificationExecutor<Ward> {
    Optional<Ward> findByName(String name);
}
