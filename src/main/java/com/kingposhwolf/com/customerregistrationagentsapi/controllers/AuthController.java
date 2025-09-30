package com.kingposhwolf.com.customerregistrationagentsapi.controllers;

import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.auth.LoginForm;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.LoginResponse;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.UserDetailsDto;
import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import com.kingposhwolf.com.customerregistrationagentsapi.services.AuthenticationService;
import com.kingposhwolf.com.customerregistrationagentsapi.util.APIResponder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public APIResponder<LoginResponse> login(@RequestBody @Valid LoginForm loginForm){
        return authenticationService.login(loginForm);
    }

    @GetMapping(value = "/me")
    public APIResponder<UserDetailsDto> me(@AuthenticationPrincipal User user) {
        if (user == null) {
            return APIResponder.of(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return APIResponder.of(HttpStatus.OK, "Details fetched successful", user.toUserDetails());
    }

    @PostMapping("/logout")
    public APIResponder<String> logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }

}
