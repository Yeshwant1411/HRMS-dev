package com.example.auth.service;


import com.example.auth.dto.UserResponseDto;
import com.example.auth.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    
    public UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}