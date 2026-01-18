package com.example.financeproject.services.account;

import com.example.financeproject.dto.dtoAccount.AccountDto;

import com.example.financeproject.dto.dtoAccount.AccountToTransferDto;
import com.example.financeproject.dto.dtoAccount.GetAccountDto;
import com.example.financeproject.dto.dtoAccount.UpdateAccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountDto getAccountById(Long accountId);

    AccountDto createAccount(AccountDto dto);

    void deleteAccount(Long accountId);

    UpdateAccountDto editAccount(Long id, UpdateAccountDto dto);

    List<GetAccountDto> getAllAccounts(Long userId);

    AccountToTransferDto transferMoneyB(AccountToTransferDto accountsToTransfer);



}
