package com.example.financeproject.dto.dtoTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionExpenseDto {

    private String name;
    private BigDecimal amount;
    private Long defaultCategoryId;
    private Long categoryId;
    private LocalDateTime date;

}
