package com.kingposhwolf.com.customerregistrationagentsapi.exceptions;

import com.kingposhwolf.com.customerregistrationagentsapi.util.APIResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NIDA_CONSTRAINT = "uk_customers_nida";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public APIResponder<?> dataIntegrityViolation(DataIntegrityViolationException exception) {
        if (violates(exception, NIDA_CONSTRAINT)) {
            return APIResponder.of(HttpStatus.CONFLICT, "A customer with this NIDA is already registered");
        }
        log.error("Unexpected data integrity violation", exception);
        return APIResponder.of(HttpStatus.INTERNAL_SERVER_ERROR, "Request could not be saved");
    }

    private static boolean violates(Throwable exception, String constraintName) {
        for (Throwable t = exception; t != null; t = t.getCause()) {
            if (t instanceof org.hibernate.exception.ConstraintViolationException cve
                    && constraintName.equalsIgnoreCase(cve.getConstraintName())) {
                return true;
            }
        }
        return false;
    }

    @ExceptionHandler(GlobalException.class)
    public APIResponder<?> globalException(GlobalException exception){
        return APIResponder.of(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponder<?> validationException(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getAllErrors().stream().map(
                error -> {if(error instanceof FieldError fe){
                    return fe.getField() + " : " + fe.getDefaultMessage();
                }
                    return error.getDefaultMessage();
                }
        ).findFirst().orElse("Validation failed");
        return APIResponder.of(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public APIResponder<?> invalidCredentialsException(BadCredentialsException exception){
        return APIResponder.of(HttpStatus.UNAUTHORIZED, exception.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public APIResponder<?> anyOtherException(Exception exception){
        log.error("Unhandled exception", exception);
        return APIResponder.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }
}
