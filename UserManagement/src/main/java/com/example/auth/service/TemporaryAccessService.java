package com.example.auth.service;

import com.example.auth.dto.TemporaryAccessRequestDto;
import com.example.auth.model.Role;
import com.example.auth.model.TemporaryAccess;
import com.example.auth.model.User;
import com.example.auth.repository.TemporaryAccessRepository;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TemporaryAccessService {

    @Autowired
    private TemporaryAccessRepository temporaryAccessRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TemporaryAccess grantTemporaryAccess(TemporaryAccessRequestDto requestDto, String hrUsername) {
        User employee = userRepository.findById(requestDto.getUserId())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (employee.getRole() != Role.EMPLOYEE) {
            throw new IllegalArgumentException("Temporary access can only be granted to employees");
        }

        User hr = userRepository.findByUsername(hrUsername)
            .orElseThrow(() -> new UsernameNotFoundException("HR user not found"));

        if (hr.getRole() != Role.HR) {
            throw new IllegalArgumentException("Only HR can grant temporary access");
        }

        TemporaryAccess temporaryAccess = new TemporaryAccess();
        temporaryAccess.setUser(employee);
        temporaryAccess.setStartTime(LocalDateTime.now());
        temporaryAccess.setEndTime(requestDto.getEndTime());
        temporaryAccess.setActive(true);
        temporaryAccess.setGrantedBy(hr);
        temporaryAccess.setReason(requestDto.getReason());

        return temporaryAccessRepository.save(temporaryAccess);
    }

    public boolean hasTemporaryAccess(User user) {
        if (user.getRole() == Role.HR) {
            return false; // HR already has full access
        }

        return temporaryAccessRepository
            .findByUserAndActiveIsTrueAndEndTimeAfter(user, LocalDateTime.now())
            .isPresent();
    }
}