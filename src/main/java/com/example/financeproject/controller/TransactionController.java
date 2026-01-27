package com.example.financeproject.controller;


import com.example.financeproject.dto.dtoTransaction.TransactionDto;
import com.example.financeproject.dto.dtoTransaction.TransactionExpenseDto;
import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.dto.dtoTransaction.UpdateTransactionDto;
import com.example.financeproject.services.transaction.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping ("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.
                status(HttpStatus.CREATED).body(transactionService.addTransaction(transactionDto));
    }
    @PatchMapping("/update_transaction/{transactionId}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long transactionId,@RequestBody UpdateTransactionDto transactionDto) {
       transactionService.updateTransaction(transactionDto,transactionId);
       return ResponseEntity.ok().body("Transaction successfully updated");
    }
    @DeleteMapping("/delete_transaction/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().body("Transaction successfully deleted");
    }

    @GetMapping("/get_all_transactions/{accountId}")
    public ResponseEntity<List<TransactionDto>> getAllTransaction(@PathVariable Long accountId){
        List<TransactionDto> dto=transactionService.getAllTransactions(accountId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @GetMapping("/filter")
    public List<TransactionDto> getFilteredTransactions(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long defaultCategoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount
    ) {
        TransactionFilterDto filter = new TransactionFilterDto(
                categoryId,
                defaultCategoryId,
                fromDate,
                toDate,
                minAmount,
                maxAmount
        );

        return transactionService.getFilteredTransactions(filter);
    }

    @GetMapping("/expenses_summary")
    public List<TransactionExpenseDto> getExpensesSummary(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long defaultCategoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount
    ) {
        TransactionFilterDto filter = new TransactionFilterDto(
                categoryId,
                defaultCategoryId,
                fromDate,
                toDate,
                minAmount,
                maxAmount
        );

        return transactionService.getExpensesSummary(filter);
    }



}
