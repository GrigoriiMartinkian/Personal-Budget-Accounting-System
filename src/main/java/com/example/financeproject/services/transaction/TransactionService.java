package com.example.financeproject.services.transaction;

import com.example.financeproject.dto.dtoAccount.GetAccountDto;
import com.example.financeproject.dto.dtoTransaction.TransactionDto;
import com.example.financeproject.dto.dtoTransaction.TransactionExpenseSDto;
import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.dto.dtoTransaction.UpdateTransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    TransactionDto addTransaction(TransactionDto dto);

    UpdateTransactionDto updateTransaction(UpdateTransactionDto dto, Long transactionId);

    void deleteTransaction(Long transactionId);

    List<TransactionDto> getAllTransactions(Long accountId);

    List<TransactionDto> getFilteredTransactions(TransactionFilterDto filterDto);

    List<TransactionExpenseSDto> getExpensesSummary(TransactionFilterDto filterDto);

}
