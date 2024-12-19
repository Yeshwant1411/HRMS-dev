package com.example.auth.service;

import com.example.auth.dto.UserRegistrationDto;
import com.example.auth.exception.ApiException;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validateRegistrationInput(UserRegistrationDto registrationDto) {
        validateRequiredFields(registrationDto);
        validateEmailFormat(registrationDto.getEmail());
        validateUniqueConstraints(registrationDto);
    }

    private void validateRequiredFields(UserRegistrationDto registrationDto) {
        if (registrationDto.getUsername() == null || registrationDto.getUsername().trim().isEmpty()) {
            throw new ApiException("Username is required", HttpStatus.BAD_REQUEST);
        }
        if (registrationDto.getEmail() == null || registrationDto.getEmail().trim().isEmpty()) {
            throw new ApiException("Email is required", HttpStatus.BAD_REQUEST);
        }
        if (registrationDto.getPassword() == null || registrationDto.getPassword().trim().isEmpty()) {
            throw new ApiException("Password is required", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            throw new ApiException("Invalid email format", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateUniqueConstraints(UserRegistrationDto registrationDto) {
        userRepository.findByUsername(registrationDto.getUsername())
            .ifPresent(u -> {
                throw new ApiException("Username already exists", HttpStatus.CONFLICT);
            });

        userRepository.findByEmail(registrationDto.getEmail())
            .ifPresent(u -> {
                throw new ApiException("Email address is already registered", HttpStatus.CONFLICT);
            });
    }
}