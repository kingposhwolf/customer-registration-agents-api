package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth;

import com.kingposhwolf.com.customerregistrationagentsapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String username;
    private Role role;
    private String phoneNumber;
    private String token;
}