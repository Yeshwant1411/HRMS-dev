package com.example.auth.config;

import com.example.auth.util.JwtSecretGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {
    private String secret;
    private long expiration;
    private final Environment environment;

    public JwtConfig(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            // Generate a secure secret if not provided
            secret = JwtSecretGenerator.generateSecureSecret();
            System.out.println("Generated JWT Secret: " + secret);
            System.out.println("Add this to your application.properties:");
            System.out.println("app.jwt.secret=" + secret);
        }
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}