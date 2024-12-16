package com.example.auth.util;

import com.example.auth.security.jwt.JwtProperties;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class JwtConfigManager {
    
    private final JwtProperties jwtProperties;

    public JwtConfigManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.trim().isEmpty()) {
            secret = JwtSecretGenerator.generateSecureSecret();
            System.out.println("\n=== JWT Configuration ===");
            System.out.println("A new JWT secret has been generated.");
            System.out.println("\nAdd the following to your application.properties:");
            System.out.println("app.jwt.secret=" + secret);
            System.out.println("\nIMPORTANT: Save this secret in a secure location!");
            System.out.println("======================\n");
        }
    }
}