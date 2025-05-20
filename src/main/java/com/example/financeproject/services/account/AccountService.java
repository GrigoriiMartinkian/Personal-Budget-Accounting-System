package com.example.financeproject.services.account;

import com.example.financeproject.dto.AccountDto;
import com.example.financeproject.mappers.AccountMapper;
import com.example.financeproject.models.*;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return accountMapper.toDto(account);
    }

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }
    @Transactional
    public Account createAccount(AccountDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        Account account = accountMapper.toEntity(dto);
        account.setUser(user);

        return accountRepository.save(account);
    }
    @Transactional
    public Account deleteAccount(AccountDto dto){
        User user=userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Account account=accountMapper.toEntity(dto);

        return accountRepository.delete(account);
    }



}


