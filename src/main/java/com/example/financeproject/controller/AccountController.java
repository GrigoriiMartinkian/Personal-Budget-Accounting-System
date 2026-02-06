package com.example.financeproject.controller;


import com.example.financeproject.dto.dtoAccount.*;

import com.example.financeproject.services.account.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto dto) {
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
        return ResponseEntity.ok().body(accounts);
    }

    @PatchMapping("/transfer")
    public ResponseEntity<TransferResponseDto> transferMoneyBetween(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        return ResponseEntity.ok().body(accountService.transfer(transferRequestDto));
    }


}


