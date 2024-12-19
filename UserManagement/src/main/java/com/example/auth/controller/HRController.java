package com.example.auth.controller;

import com.example.auth.dto.*;
import com.example.auth.model.Role;
import com.example.auth.model.TemporaryAccess;
import com.example.auth.service.TemporaryAccessService;
import com.example.auth.service.UserService;
import com.example.auth.security.jwt.JwtAuthResponse;
import com.example.auth.security.jwt.JwtProperties;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/hr")
@PreAuthorize("hasRole('HR')")
public class HRController {

    @Autowired
    private UserService userService;

    @Autowired
    private TemporaryAccessService temporaryAccessService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/grant-temporary-access")
    public ResponseEntity<?> grantTemporaryAccess(
            @Valid @RequestBody TemporaryAccessRequestDto requestDto,
            Authentication authentication) {
        try {
            TemporaryAccess access = temporaryAccessService.grantTemporaryAccess(
                requestDto, 
                authentication.getName()
            );
            return ResponseEntity.ok(access);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register/hr")
    public ResponseEntity<?> registerHR(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            JwtAuthResponse response = userService.registerUser(registrationDto, Role.HR);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register/employee")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            JwtAuthResponse response = userService.registerUser(registrationDto, Role.EMPLOYEE);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> hrDashboard() {
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to HR Dashboard",
            "access", "Full administrative access"
        ));
    }

    @GetMapping("/config/jwt-status")
    public ResponseEntity<?> getJwtStatus() {
        boolean isConfigured = jwtProperties.getSecret() != null 
            && !jwtProperties.getSecret().trim().isEmpty();
        
        return ResponseEntity.ok(Map.of(
            "jwtConfigured", isConfigured,
            "expirationMs", jwtProperties.getExpiration()
        ));
    }
}
