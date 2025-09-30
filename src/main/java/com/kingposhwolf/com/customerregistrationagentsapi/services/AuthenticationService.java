package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.auth.LoginForm;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.LoginResponse;
import com.kingposhwolf.com.customerregistrationagentsapi.util.APIResponder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public APIResponder<LoginResponse> login(LoginForm loginForm){
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getPhoneNumber(), loginForm.getPassword()));
        User user = (User) authentication.getPrincipal();
        LoginResponse response = LoginResponse.builder()
                .username(user.getUsername())
                .token(jwtService.generateToken(user))
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
        return APIResponder.of(HttpStatus.OK, "Login Successful", response);
    }
}