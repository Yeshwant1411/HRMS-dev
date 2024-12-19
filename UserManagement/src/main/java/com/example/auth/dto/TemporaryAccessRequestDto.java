package com.example.auth.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TemporaryAccessRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotBlank(message = "Reason is required")
    private String reason;
}