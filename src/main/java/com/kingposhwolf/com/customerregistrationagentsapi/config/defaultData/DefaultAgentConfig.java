package com.kingposhwolf.com.customerregistrationagentsapi.config.defaultData;

import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import com.kingposhwolf.com.customerregistrationagentsapi.enums.Role;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultAgentConfig {
    @Bean
    CommandLineRunner initAgent(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            String username = "agent001";
            String password = "password";
            String phoneNumber = "+255692428600";
            Role role = Role.AGENT;

            userRepository.findByUsername(username).ifPresentOrElse(user -> System.out.println("Agent already exists"),
                    () -> {
                User agent = new User();
                agent.setUsername(username);
                agent.setPassword(passwordEncoder.encode(password));
                agent.setRole(role);
                agent.setPhoneNumber(phoneNumber);

                userRepository.save(agent);
                System.out.println("Admin created");
            });
        };
    }
}
