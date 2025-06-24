package com.example.financeproject.repositories.account;

import com.example.financeproject.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
 List<Account> findAllByUserId(Long userId);
}

