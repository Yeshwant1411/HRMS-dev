package com.example.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static String generateSecureSecret() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secret = new byte[64]; 
        secureRandom.nextBytes(secret);
        return Base64.getEncoder().encodeToString(secret);
    }
}