package com.example.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.auth.model.LeaveRequest;
import com.example.auth.service.LeaveService;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final LeaveService leaveService;

    @PostMapping("/{employeeId}/leave-requests")
    public LeaveRequest requestLeave(@PathVariable Long employeeId, @RequestBody LeaveRequest leaveRequest) {
        return leaveService.createLeaveRequest(employeeId, leaveRequest);
    }
}