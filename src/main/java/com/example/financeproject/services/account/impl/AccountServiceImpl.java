package com.example.financeproject.services.account.impl;

import com.example.financeproject.dto.AccountDto;

import com.example.financeproject.mappers.AccountMapper;
import com.example.financeproject.models.*;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.user.UserRepository;

import com.example.financeproject.services.account.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Service

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;


    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return accountMapper.toDto(account);
    }

    @Transactional
    public Account createAccount(AccountDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        Account account = accountMapper.toEntity(dto);
        account.setUser(user);
        account.setAccountNumber(generateAccountNumber(dto));

        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        accountRepository.delete(account);
    }

    @Transactional
    public AccountDto editAccount(Long id, AccountDto dto) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        if (dto.getName() != null) {
            account.setName(dto.getName());
        }
        if (dto.getBalance() != null) {
            account.setBalance(dto.getBalance());
        }
        if (dto.getType() != null) {
            account.setType(dto.getType());

        }
        if (dto.getCode() != null && dto.getExchangeRate() != null) {
            account.setCurrency(new Currency(dto.getCode(), dto.getExchangeRate()));
        }

        Account updated = accountRepository.save(account);
        return accountMapper.toDto(updated);
    }

    @Transactional
    public List<AccountDto> getAllAccounts(Long userId) {
        List<Account> accounts = accountRepository.findAllByUserId(userId);


        if (accounts.isEmpty()) {
            throw new EntityNotFoundException("Accounts not found");
        }

        System.out.println("До маппинга: категории первого аккаунта = " + accounts.get(0).getCategories().size());
        return accounts.stream()
                .peek(account -> account.getCategories().size()) // принудительно инициализируем
                .map(accountMapper::toDto)
                .toList();

    }







    private String generateAccountNumber(AccountDto dto) {

        StringBuilder sb = new StringBuilder();

        String namePart = (dto.getName() != null && dto.getName().length() >= 2)
                ? dto.getName().substring(0, 2).toUpperCase()
                : "XX";
        sb.append(namePart);


        String userIdPart = String.valueOf(dto.getUserId());
        sb.append(userIdPart);


        String typeChar = (dto.getType() != null)
                ? dto.getType().name().substring(1, 2).toUpperCase()
                : "X";


        SecureRandom random = new SecureRandom();
        StringBuilder randomPart = new StringBuilder();
        while (sb.length() + randomPart.length() + 1 < 15) { // +1 на тип
            randomPart.append(random.nextInt(10));
        }


        StringBuilder finalBuilder = new StringBuilder();
        finalBuilder.append(namePart);

        int remainingLength = 15 - namePart.length() - randomPart.length() - 1;
        if (userIdPart.length() > remainingLength) {

            userIdPart = userIdPart.substring(userIdPart.length() - remainingLength);
        }
        finalBuilder.append(userIdPart);
        finalBuilder.append(typeChar);
        finalBuilder.append(randomPart);


        return finalBuilder.toString();


    }


}


