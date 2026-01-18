package com.example.financeproject.dto.dtoAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountToTransferDto {
    private  Long idSender;
    private  Long idReceiver;
    private String nameSender;
    private String nameReceiver;
    private BigDecimal balanceSender;
    private BigDecimal balanceReceiver;
    private LocalDateTime balanceDate=LocalDateTime.now();
    private BigDecimal amount;


}
