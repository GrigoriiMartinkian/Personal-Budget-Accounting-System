package com.example.financeproject.dto;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CurrencyDto {
    private String code;
    private BigDecimal exchangeRate;

    public CurrencyDto(String code, BigDecimal exchangeRate) {
        this.code = code;
        this.exchangeRate = exchangeRate;
    }




}
