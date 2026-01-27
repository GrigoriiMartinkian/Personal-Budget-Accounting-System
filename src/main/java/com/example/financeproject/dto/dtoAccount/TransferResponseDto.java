package com.example.financeproject.dto.dtoAccount;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDto {

    private BigDecimal fromBalance;
    private BigDecimal toBalance;
    private LocalDateTime date;
    private BigDecimal amount;

}

