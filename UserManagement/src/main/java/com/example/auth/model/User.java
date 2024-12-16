package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
	    @UniqueConstraint(columnNames = "username"),
	    @UniqueConstraint(columnNames = "email")
	})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    @JsonIgnore // This will exclude the password from JSON serialization
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;
    

    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}