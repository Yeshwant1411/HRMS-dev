package com.example.auth.dto;

import com.example.auth.model.LeaveStatus;
import com.example.auth.model.LeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDto {
    private Long id;
    private String employeeName;
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    @NotNull(message = "Leave type is required")
    private LeaveType leaveType;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    private LeaveStatus status;
    private LocalDate appliedDate;
}