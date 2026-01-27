package com.example.financeproject.services.transaction;

import com.example.financeproject.dto.dtoTransaction.TransactionDto;
import com.example.financeproject.dto.dtoTransaction.TransactionExpenseDto;
import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.dto.dtoTransaction.UpdateTransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto addTransaction(TransactionDto dto);

    UpdateTransactionDto updateTransaction(UpdateTransactionDto dto, Long transactionId);

    void deleteTransaction(Long transactionId);

    List<TransactionDto> getAllTransactions(Long accountId);

    List<TransactionDto> getFilteredTransactions(TransactionFilterDto filterDto);

    List<TransactionExpenseDto> getExpensesSummary(TransactionFilterDto filterDto);

}
