package com.example.financeproject.dto.dtoAccount;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferResponseDto {

    private BigDecimal fromBalance;
    private BigDecimal toBalance;
    private LocalDateTime date;
    private BigDecimal amount;

}

