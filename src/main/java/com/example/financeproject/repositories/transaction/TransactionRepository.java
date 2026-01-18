package com.example.financeproject.repositories.transaction;

import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findAllByAccountId(Long accountId);

}
