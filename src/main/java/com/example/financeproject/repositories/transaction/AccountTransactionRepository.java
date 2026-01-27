package com.example.financeproject.repositories.transaction;

import com.example.financeproject.models.AccountTransaction;
import com.example.financeproject.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long>, JpaSpecificationExecutor<Transaction> {

}
