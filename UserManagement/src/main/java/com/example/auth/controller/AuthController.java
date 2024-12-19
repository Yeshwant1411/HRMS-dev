package com.example.auth.controller;

import com.example.auth.security.JwtTokenProvider;
import com.example.auth.security.jwt.JwtAuthResponse;
import com.example.auth.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller for handling authentication and role-specific dashboard access.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Authenticates a user and generates a JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        String role = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("")
            .replace("ROLE_", "");

        JwtAuthResponse response = new JwtAuthResponse(jwt, loginRequest.getUsername(), role);
        return ResponseEntity.ok(response);
    }

    /**
     * Provides access to the HR dashboard.
     * Requires HR role.
     */
    @GetMapping("/hr/dashboard")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> hrDashboard() {
        return ResponseEntity.ok(Map.of("message", "Welcome to HR Dashboard"));
    }

    /**
     * Provides access to the employee dashboard.
     * Requires EMPLOYEE role.
     */
    @GetMapping("/employee/dashboard")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> employeeDashboard() {
        return ResponseEntity.ok(Map.of("message", "Welcome to Employee Dashboard"));
    }
}