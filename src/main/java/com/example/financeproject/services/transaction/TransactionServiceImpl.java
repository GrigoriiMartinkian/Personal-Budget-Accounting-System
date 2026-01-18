package com.example.financeproject.services.transaction;

import com.example.financeproject.dto.dtoTransaction.TransactionDto;
import com.example.financeproject.dto.dtoTransaction.TransactionExpenseSDto;
import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.dto.dtoTransaction.UpdateTransactionDto;
import com.example.financeproject.mappers.TransactionMapper;
import com.example.financeproject.models.Account;
import com.example.financeproject.models.Category;
import com.example.financeproject.models.DefaultCategory;
import com.example.financeproject.models.Transaction;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.category.CategoryRepository;
import com.example.financeproject.repositories.defaultCategory.DefaultCategoryRepository;
import com.example.financeproject.repositories.transaction.TransactionRepository;


import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;



import java.util.ArrayList;

import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final DefaultCategoryRepository defaultCategoryRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository
            , AccountRepository accountRepository,
                                  CategoryRepository categoryRepository,
                                  TransactionMapper transactionMapper,
                                  DefaultCategoryRepository defaultCategoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.transactionMapper = transactionMapper;
        this.defaultCategoryRepository = defaultCategoryRepository;
    }




    public TransactionDto addTransaction(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        if (dto.getCategoryId() == null) {

            DefaultCategory defaultCategory = defaultCategoryRepository.findById(dto.getDefaultCategoryId())
                    .orElseThrow(() -> new NotFoundException("Default category not found"));

            Transaction transaction = Transaction.builder()
                    .account(account)
                    .defaultCategory(defaultCategory)
                    .amount(dto.getAmount())
                    .description(dto.getDescription())

                    .build();

            return transactionMapper.transactionToTransactionDefDto(transactionRepository.save(transaction));

        } else {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));

            Transaction transaction = Transaction.builder()
                    .account(account)
                    .userCategory(category)
                    .amount(dto.getAmount())
                    .description(dto.getDescription())

                    .build();

            return transactionMapper.transactionToTransactionUsCatDto(transactionRepository.save(transaction));
        }
    }

    public UpdateTransactionDto updateTransaction(UpdateTransactionDto dto, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (dto.getAmount() != null) {
            transaction.setAmount(dto.getAmount());
        }
        if (dto.getDescription() != null) {
            transaction.setDescription(dto.getDescription());
        }
        if (dto.getDate() != null) {
            transaction.setDate(dto.getDate());
        }
        System.out.println("updateTransaction");
        return transactionMapper.toUpdateTransactionDto(transactionRepository.save(transaction));
    }

    public void deleteTransaction(Long transactionId) {
        Transaction deleteTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        transactionRepository.delete(deleteTransaction);
    }

    public List<TransactionDto> getAllTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }

        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getUserCategory() != null) {
                transactionDtos.add(transactionMapper.transactionToTransactionUsCatDto(transaction));
            } else {
                transactionDtos.add(transactionMapper.transactionToTransactionDefDto(transaction));
            }
        }
        return transactionDtos;
    }

   public List<TransactionDto> getFilteredTransactions(TransactionFilterDto filter) {
       List<Transaction> transactions = transactionRepository.findAll(TransactionSpecification.withFilter(filter));
       return transactions.stream()
               .map(transactionMapper::transactionToTransactionDto)
               .toList();
   }

   public List<TransactionExpenseSDto> getExpensesSummary(TransactionFilterDto filter){
        List<Transaction> transactions=transactionRepository.findAll(TransactionSpecification.withFilter(filter));
        List<TransactionExpenseSDto> dtos=new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionExpenseSDto tdto=new TransactionExpenseSDto(transaction.getUserCategory().getName(),transaction.getAmount());
              dtos.add(tdto);
        }
        return dtos;
   }



}
