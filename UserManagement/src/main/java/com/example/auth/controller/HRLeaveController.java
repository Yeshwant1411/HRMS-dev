package com.example.auth.controller;

import com.example.auth.dto.LeaveRequestDto;
import com.example.auth.dto.LeaveStatusUpdateDto;
import com.example.auth.model.LeaveStatus;
import com.example.auth.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class HRLeaveController {
    private final LeaveService leaveService;

    @GetMapping("/leave-requests")
    public List<LeaveRequestDto> getAllLeaveRequests() {
        return leaveService.getAllLeaveRequests();
    }

    @GetMapping("/leave-requests/status/{status}")
    public List<LeaveRequestDto> getLeaveRequestsByStatus(@PathVariable String status) {
        try {
            LeaveStatus leaveStatus = LeaveStatus.valueOf(status.toUpperCase());
            return leaveService.getLeaveRequestsByStatus(leaveStatus);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Invalid status value: " + status
            );
        }
    }

    @PutMapping("/leave-requests/{requestId}/status")
    public LeaveRequestDto updateLeaveStatus(
        @PathVariable Long requestId,
        @RequestBody LeaveStatusUpdateDto updateDto
    ) {
        return leaveService.updateLeaveStatus(
            requestId, 
            updateDto.getStatus(), 
            updateDto.getHrComment()
        );
    }
}