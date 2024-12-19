package com.example.auth.security.jwt;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String role;
    private String redirectUrl;

    public JwtAuthResponse(String accessToken, String username, String role) {
        this.accessToken = accessToken;
        this.username = username;
        this.role = role;
        this.redirectUrl = determineRedirectUrl(role);
    }

    private String determineRedirectUrl(String role) {
        return switch (role) {
            case "HR" -> "/hr/dashboard";
            case "EMPLOYEE" -> "/employee/dashboard";
            default -> "/";
        };
    }
}