package com.kingposhwolf.com.customerregistrationagentsapi.util;

import com.kingposhwolf.com.customerregistrationagentsapi.annotations.UniqueNida;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.customer.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueNidaValidator implements ConstraintValidator<UniqueNida, String> {
    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String nida, ConstraintValidatorContext context) {
        if (nida == null || nida.isBlank()) {
            return true;
        }
        return !customerRepository.existsByNida(nida);
    }
}

