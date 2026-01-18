package com.example.financeproject.dto.dtoAccount;

import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.models.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private Long userId;
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime balanceDate=LocalDateTime.now();
    private AccountType type;
    private String code;
    private BigDecimal exchangeRate;
    private List<CategoryDto> categories = new ArrayList<>();



}


