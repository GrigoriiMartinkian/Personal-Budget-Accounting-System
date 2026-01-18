package com.example.financeproject.dto.dtoTransaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionFilterDto {
        private Long categoryId;
        private Long DefaultCategory;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private BigDecimal minAmount;
        private BigDecimal maxAmount;

        public Object getDefaultCategoryId() {
                return DefaultCategory;
        }

        public LocalDate getFromDate() {
                return dateFrom;
        }

        public LocalDate getToDate() {
                return dateTo;
        }


// Геттеры и сеттеры
}
