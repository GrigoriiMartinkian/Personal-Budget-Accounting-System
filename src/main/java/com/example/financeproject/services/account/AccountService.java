package com.example.financeproject.services.account;

import com.example.financeproject.dto.AccountDto;

import com.example.financeproject.models.Account;


import java.util.List;

public interface AccountService {

    AccountDto getAccountById(Long accountId);

    Account createAccount(AccountDto dto);

    void deleteAccount(Long accountId);

    AccountDto editAccount(Long id, AccountDto dto);

    List<AccountDto> getAllAccounts(Long userId);



}
