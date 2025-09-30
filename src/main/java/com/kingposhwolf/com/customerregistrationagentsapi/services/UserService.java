package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import com.kingposhwolf.com.customerregistrationagentsapi.exceptions.GlobalException;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber){
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if(user.isEmpty()) throw new GlobalException(HttpStatus.UNAUTHORIZED, "Invalid phone number or password");
        return user.get();
    }
}
