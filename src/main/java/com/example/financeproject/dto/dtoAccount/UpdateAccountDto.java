package com.example.financeproject.dto.dtoAccount;

import com.example.financeproject.models.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAccountDto {
    private String name;
    private BigDecimal balance;
    private LocalDateTime balanceDate;
    private AccountType type;
}
