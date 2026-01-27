package com.example.financeproject.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    public ErrorResponseDto(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp=LocalDateTime.now();
    }
}