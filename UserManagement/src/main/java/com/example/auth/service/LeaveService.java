package com.example.auth.service;

import com.example.auth.dto.LeaveRequestDto;
import com.example.auth.model.LeaveRequest;
import com.example.auth.model.LeaveStatus;
import com.example.auth.model.User;
import com.example.auth.repository.LeaveRequestRepository;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<LeaveRequestDto> getAllLeaveRequests() {
        return leaveRequestRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LeaveRequestDto> getLeaveRequestsByStatus(LeaveStatus status) {
        return leaveRequestRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public LeaveRequest createLeaveRequest(Long employeeId, LeaveRequest leaveRequest) {
        // Find the employee
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Set the employee for the leave request
        leaveRequest.setEmployee(employee);

        // Set default status if not already set
        if (leaveRequest.getStatus() == null) {
            leaveRequest.setStatus(LeaveStatus.PENDING);
        }

        // Set created at timestamp
        leaveRequest.setCreatedAt(LocalDateTime.now());

        // Save the leave request
        return leaveRequestRepository.save(leaveRequest);
    }

    @Transactional
    public LeaveRequestDto updateLeaveStatus(Long requestId, LeaveStatus status, String hrComment) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        
        leaveRequest.setStatus(status);
        leaveRequest.setHrComment(hrComment);
        leaveRequest.setUpdatedAt(LocalDateTime.now());
        
        return convertToDto(leaveRequestRepository.save(leaveRequest));
    }

    private LeaveRequestDto convertToDto(LeaveRequest leaveRequest) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeName(leaveRequest.getEmployee().getUsername());
        dto.setReason(leaveRequest.getReason()); // Add this field to LeaveRequest
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setStatus(leaveRequest.getStatus());
        dto.setAppliedDate(LocalDate.now()); // Or use createdAt from LeaveRequest
        return dto;
    }
}