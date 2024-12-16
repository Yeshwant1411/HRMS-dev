package com.example.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeaveActionDto {
    @NotNull(message = "Approval status is required")
    private Boolean approved;
    
    private String comment;
}
