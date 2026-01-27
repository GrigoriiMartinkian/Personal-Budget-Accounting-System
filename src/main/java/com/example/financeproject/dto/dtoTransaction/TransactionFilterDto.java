package com.example.financeproject.dto.dtoTransaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;



@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionFilterDto {
        private Long categoryId;
        private Long defaultCategoryId;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private BigDecimal minAmount;
        private BigDecimal maxAmount;


}
