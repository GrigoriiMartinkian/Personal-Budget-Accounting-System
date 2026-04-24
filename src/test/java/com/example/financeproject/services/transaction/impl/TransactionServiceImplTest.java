package com.example.financeproject.services.transaction.impl;

import com.example.financeproject.mappers.TransactionMapper;
import com.example.financeproject.models.Transaction;
import com.example.financeproject.repositories.transaction.TransactionRepository;
import com.example.financeproject.services.transaction.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private Transaction transaction;

    @Mock
    private TransactionMapper transactionMapper;

//    @Test
//    void SuccessAddTransaction()exst{
//
//    }

}
