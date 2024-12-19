package com.example.auth.service;

import com.example.auth.dto.UserRegistrationDto;
import com.example.auth.exception.ApiException;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtTokenProvider;
import com.example.auth.security.jwt.JwtAuthResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserValidationService validationService;

    @Transactional
    public JwtAuthResponse registerUser(UserRegistrationDto registrationDto, Role role) {
        try {
            validationService.validateRegistrationInput(registrationDto);
            User user = createUserFromDto(registrationDto, role);
            user = userRepository.save(user);
            
            // Using the correct generateToken method that accepts username and role
            String token = tokenProvider.generateToken(user.getUsername(), role.name());
            return new JwtAuthResponse(token, user.getUsername(), role.name());
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolation(e);
            return null; // This line will never be reached due to exception handling
        } catch (Exception e) {
            throw new ApiException("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private User createUserFromDto(UserRegistrationDto registrationDto, Role role) {
        User user = new User();
        user.setUsername(registrationDto.getUsername().trim());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail().trim().toLowerCase());
        user.setRole(role);
        return user;
    }

    private void handleDataIntegrityViolation(DataIntegrityViolationException e) {
        if (e.getMessage().contains("users.UK") && e.getMessage().contains("email")) {
            throw new ApiException("Email address is already registered", HttpStatus.CONFLICT);
        }
        throw new ApiException("Username or email already exists", HttpStatus.CONFLICT);
    }
}