package com.example.financeproject.dto.dtoTransaction;

import com.example.financeproject.validation.CategoryValidators.ValidCategorySelection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

import java.time.LocalDateTime;

@ValidCategorySelection
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {

    private Long id;

    @NotNull
    private Long accountId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    private Long defaultCategoryId;
    private Long categoryId;

    private LocalDateTime date = LocalDateTime.now();

    @Size(max = 255)
    private String description;
}