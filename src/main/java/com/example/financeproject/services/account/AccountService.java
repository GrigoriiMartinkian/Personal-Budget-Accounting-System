package com.example.financeproject.services.account;

import com.example.financeproject.dto.dtoAccount.*;


import java.util.List;

public interface AccountService {

    AccountDto getAccountById(Long accountId);

    AccountDto createAccount(AccountDto dto);

    void deleteAccount(Long accountId);

    UpdateAccountDto editAccount(Long id, UpdateAccountDto dto);

    List<GetAccountDto> getAllAccounts(Long userId);

    TransferResponseDto transfer(TransferRequestDto dto);



}
