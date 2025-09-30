package com.kingposhwolf.com.customerregistrationagentsapi.repositories;

import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
}