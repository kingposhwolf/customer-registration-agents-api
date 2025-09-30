package com.kingposhwolf.com.customerregistrationagentsapi.repositories.customer;

import com.kingposhwolf.com.customerregistrationagentsapi.common.repositories.BaseRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomerRepositoryCustom extends BaseRepository<Customer, Long> {

    public CustomerRepositoryCustom(CustomerRepository jpaRepository) {
        super(
                jpaRepository,
                List.of("name", "nida"),
                Map.of("registeredBy", "registeredBy.id")
        );
    }
}
