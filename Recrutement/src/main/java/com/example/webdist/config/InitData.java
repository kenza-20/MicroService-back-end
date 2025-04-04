package com.example.webdist.config;

import com.example.webdist.entity.User;
import com.example.webdist.entity.Role;
import com.example.webdist.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Configuration
public class InitData {
    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create HR user if not exists
            if (userRepository.findByUsername("hr").isEmpty()) {
                User hrUser = new User("hr", passwordEncoder.encode("password"));
                hrUser.setRoles(List.of(Role.HR));
                userRepository.save(hrUser);
                System.out.println("HR user created!");
            }

            // Create regular user if not exists
            if (userRepository.findByUsername("user").isEmpty()) {
                User regularUser = new User("user", passwordEncoder.encode("password"));
                regularUser.setRoles(List.of(Role.USER));
                userRepository.save(regularUser);
                System.out.println("Regular user created!");
            }
        };
    }
}
