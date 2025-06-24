package com.example.financeproject.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.financeproject.models.Account;
import com.example.financeproject.services.account.impl.AccountServiceImpl;
import com.example.financeproject.dto.AccountDto;

import java.util.List;

@RestController
@RequestMapping("/accounts")

public class AccountController {

    private final AccountServiceImpl accountService;
    private final AccountServiceImpl accountServiceImpl;

    public AccountController(AccountServiceImpl accountService, AccountServiceImpl accountServiceImpl) {
        this.accountService = accountService;
        this.accountServiceImpl = accountServiceImpl;
    }


    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto dto) {
        Account createdAccount = accountService.createAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @DeleteMapping("/delete_account/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account successfully deleted");
    }

    @PatchMapping("/update_account/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @RequestBody AccountDto dto) {
        accountService.editAccount(id, dto);
        return ResponseEntity.ok("Account successfully Edited");
    }

    @GetMapping("/get_all/{userId}")
    public ResponseEntity<List<AccountDto>> getAllAccounts(@PathVariable Long userId) {
      List<AccountDto> accounts= accountService.getAllAccounts(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(accounts);
    }


}


