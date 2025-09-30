package com.kingposhwolf.com.customerregistrationagentsapi.exceptions;

import com.kingposhwolf.com.customerregistrationagentsapi.util.APIResponder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
        return APIResponder.of(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());
    }
}