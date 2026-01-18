package com.example.financeproject.controller;


import com.example.financeproject.dto.dtoAccount.AccountToTransferDto;
import com.example.financeproject.dto.dtoAccount.GetAccountDto;
import com.example.financeproject.dto.dtoAccount.UpdateAccountDto;

import com.example.financeproject.services.account.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.financeproject.dto.dtoAccount.AccountDto;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountService accountService;

    //
    public AccountController(AccountService accountService) {
        this.accountService = accountService;

    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto dto) {
        AccountDto createdAccount = accountService.createAccount(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @DeleteMapping("/delete_account/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account successfully deleted");
    }

    @PatchMapping("/update_account/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountDto dto) {
        accountService.editAccount(id, dto);
        return ResponseEntity.ok("Account successfully Edited");
    }

    @GetMapping("/get_all/{userId}")
    public ResponseEntity<List<GetAccountDto>> getAllAccounts(@PathVariable Long userId) {
        List<GetAccountDto> accounts = accountService.getAllAccounts(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(accounts);
    }

    @PatchMapping("/transfer")
    public ResponseEntity<String> transferMoneyBetween(@RequestBody AccountToTransferDto accountToTransfer) {

        accountService.transferMoneyB(accountToTransfer);
        return ResponseEntity.ok("Account successfully Edited");

    }


}


