package com.kingposhwolf.com.customerregistrationagentsapi.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class APIResponder<T> extends ResponseEntity<APIResponder.APIFields> {

    @AllArgsConstructor
    @Getter
    protected static class APIFields {
        private String message;

        private static APIFields of(String message){
            return new APIFields(message);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    protected static class APIDataFields<T> extends APIFields{
        private final T data;

        public APIDataFields(T data, String message){
            super(message);
            this.data = data;
        }

        private static <S> APIDataFields<S> of(S data, String message){
            return new APIDataFields<>(data, message);
        }
    }

    private APIResponder(HttpStatusCode status, String message){
        super(APIFields.of(message), status);
    }

    private APIResponder(HttpStatusCode status, String message, T data){
        super(APIDataFields.of(data, message), status);
    }

    public static <S> APIResponder<S> of(HttpStatusCode status, String message){
        return new APIResponder<>(status, message);
    }

    public static <S> APIResponder<S> of(HttpStatusCode status, String message, S data){
        return new APIResponder<>(status, message, data);
    }
}