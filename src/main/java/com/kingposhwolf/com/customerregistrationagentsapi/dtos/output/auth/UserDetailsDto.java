package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String name;
    private String phone;
    private Location location;
    private boolean status;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Location {
        private LocationValue region;
        private LocationValue district;
        private LocationValue ward;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LocationValue {
        private Long id;
        private String name;
    }
}