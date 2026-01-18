package com.example.financeproject.dto.dtoTransaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private Long defaultCategoryId; // Дефолтная категория (если выбрана)
    private Long categoryId; // Пользовательская категория (если выбрана)
    private LocalDateTime date = LocalDateTime.now();
    private String description;

}
