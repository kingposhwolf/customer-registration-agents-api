package com.kingposhwolf.com.customerregistrationagentsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CustomerRegistrationAgentsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerRegistrationAgentsApiApplication.class, args);
    }

}
