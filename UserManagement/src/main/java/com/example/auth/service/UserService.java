package com.example.auth.service;

import com.example.auth.dto.UserRegistrationDto;
import com.example.auth.dto.UserResponseDto;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.jwt.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserRegistrationService registrationService;
    
    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
    
    
    @Transactional
    public JwtAuthResponse registerUser(UserRegistrationDto registrationDto, Role role) {
        return registrationService.registerUser(registrationDto, role);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userDtoMapper::convertToDto)
            .collect(Collectors.toList());
    }
}