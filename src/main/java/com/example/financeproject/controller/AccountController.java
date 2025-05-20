package com.example.financeproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.financeproject.models.Account;
import com.example.financeproject.services.account.AccountService;
import com.example.financeproject.dto.AccountDto;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto dto) {
        Account createdAccount = accountService.createAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
}


