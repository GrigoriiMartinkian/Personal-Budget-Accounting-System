package com.example.financeproject.dto;

import com.example.financeproject.models.AccountType;
import com.example.financeproject.models.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long userId;
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime balanceDate=LocalDateTime.now();
    private AccountType type;
    private String code;
    private BigDecimal exchangeRate;
    private List<Category> categories = new ArrayList<>();


}


