package com.example.auth.config;

import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("hr_admin").isEmpty()) {
            User hrAdmin = new User();
            hrAdmin.setUsername("hr_admin");
            hrAdmin.setPassword(passwordEncoder.encode("hr_admin123"));
            hrAdmin.setEmail("hr_admin@example.com");
            hrAdmin.setRole(Role.HR);
            userRepository.save(hrAdmin);
        }
    }
}