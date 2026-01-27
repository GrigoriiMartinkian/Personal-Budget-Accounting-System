package com.example.financeproject.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CurrencyDto {
    @NotBlank
    private String code;
    private BigDecimal exchangeRate;

    public CurrencyDto(String code, BigDecimal exchangeRate) {
        this.code = code;
        this.exchangeRate = exchangeRate;
    }




}
