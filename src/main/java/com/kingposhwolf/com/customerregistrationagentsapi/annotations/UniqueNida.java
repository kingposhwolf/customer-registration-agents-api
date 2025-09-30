package com.kingposhwolf.com.customerregistrationagentsapi.annotations;

import com.kingposhwolf.com.customerregistrationagentsapi.util.UniqueNidaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueNidaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueNida {
    String message() default "NIDA must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

