package com.example.auth.dto;


import com.example.auth.model.LeaveStatus;

import lombok.Data;

@Data
public class LeaveStatusUpdateDto {
    private LeaveStatus status;
    private String hrComment;
}