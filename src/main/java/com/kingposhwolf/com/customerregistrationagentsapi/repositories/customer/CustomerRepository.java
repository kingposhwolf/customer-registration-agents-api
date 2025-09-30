package com.kingposhwolf.com.customerregistrationagentsapi.repositories.customer;

import com.kingposhwolf.com.customerregistrationagentsapi.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> findByNida(String nida);
    boolean existsByNida(String nida);
}
