package com.example.financeproject.dto;

import com.example.financeproject.models.AccountType;
import com.example.financeproject.models.Category;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountDto {
    private Long userId;
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDate balanceDate;
    private AccountType type;
    private String code;
    private BigDecimal exchangeRate;
    private List<Category> categories = new ArrayList<>();

}


