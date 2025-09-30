package com.kingposhwolf.com.customerregistrationagentsapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class GlobalException extends RuntimeException{
    private final HttpStatusCode httpStatus;

    public GlobalException(HttpStatusCode status, String message){
        super(message);
        this.httpStatus = status;
    }
}