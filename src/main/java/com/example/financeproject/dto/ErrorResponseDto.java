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
    private int status;         // HTTP-код (404, 500 и т.д.)
    private String error;       // Тип ошибки ("Not Found")
    private String message;     // Детализация ("Category with id 5 not found")
    private LocalDateTime timestamp = LocalDateTime.now();
    public ErrorResponseDto(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp=LocalDateTime.now();
    }
}