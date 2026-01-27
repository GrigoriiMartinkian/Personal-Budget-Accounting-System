package com.example.financeproject.services.transaction;

import com.example.financeproject.dto.dtoTransaction.TransactionDto;
import com.example.financeproject.dto.dtoTransaction.TransactionExpenseDto;
import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.dto.dtoTransaction.UpdateTransactionDto;
import com.example.financeproject.exception.ResourceNotFoundException;
import com.example.financeproject.mappers.TransactionMapper;
import com.example.financeproject.models.Account;
import com.example.financeproject.models.Category;
import com.example.financeproject.models.DefaultCategory;
import com.example.financeproject.models.Transaction;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.category.CategoryRepository;
import com.example.financeproject.repositories.defaultCategory.DefaultCategoryRepository;
import com.example.financeproject.repositories.transaction.TransactionRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import com.example.financeproject.models.QTransaction;


@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final DefaultCategoryRepository defaultCategoryRepository;
    private final JPAQueryFactory queryFactory;


    public TransactionServiceImpl(TransactionMapper transactionMapper,
                                  TransactionRepository transactionRepository,
                                  AccountRepository accountRepository,
                                  CategoryRepository categoryRepository,
                                  DefaultCategoryRepository defaultCategoryRepository,
                                  JPAQueryFactory queryFactory) {
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.defaultCategoryRepository = defaultCategoryRepository;
        this.queryFactory = queryFactory;
    }

    public TransactionDto addTransaction(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (dto.getCategoryId() == null) {

            DefaultCategory defaultCategory = defaultCategoryRepository.findById(dto.getDefaultCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Default category not found"));

            Transaction transaction = Transaction.builder()
                    .account(account)
                    .defaultCategory(defaultCategory)
                    .amount(dto.getAmount())
                    .description(dto.getDescription())

                    .build();

            return transactionMapper.transactionToTransactionDefDto(transactionRepository.save(transaction));

        } else {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        transactionRepository.delete(deleteTransaction);
    }

    public List<TransactionDto> getAllTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("Transaction not found");
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

        BooleanExpression predicate = TransactionPredicates.buildPredicate(filter);


        Iterable<Transaction> transactions = transactionRepository.findAll(predicate);


        return StreamSupport.stream(transactions.spliterator(), false)
                .map(transactionMapper::transactionToTransactionDto)
                .toList();
    }

//    public List<TransactionExpenseDto> getExpensesSummary(TransactionFilterDto filter) {
//        QTransaction t = QTransaction.transaction;
//
//        return queryFactory
//                .select(Projections.constructor(TransactionExpenseDto.class,
//                        t.userCategory.name,
//                        t.amount.sum(),
//                        t.defaultCategory.id,
//                        t.userCategory.id,
//                        t.date
//
//                ))
////        private String name;
////        private BigDecimal amount;
////        private Long defaultCategoryId;
////        private Long categoryId;
////        private LocalDateTime date;
//                .from(t)
//                .where(
//
//                        filter.getCategoryId() != null ? t.userCategory.id.eq(filter.getCategoryId()) : null,
//                        filter.getMinAmount() != null ? t.amount.goe(filter.getMinAmount()) : null,
//                        filter.getMaxAmount() != null ? t.amount.loe(filter.getMaxAmount()) : null,
//                        filter.getDateFrom() != null ? t.date.goe(filter.getDateFrom().atStartOfDay()) : null,
//                        filter.getDateTo() != null ? t.date.loe(filter.getDateTo().atTime(23, 59, 59)) : null
//                )
//                .groupBy(t.userCategory.name)
//                .fetch();
//    }
public List<TransactionExpenseDto> getExpensesSummary(TransactionFilterDto filter) {
    QTransaction t = QTransaction.transaction;


    BooleanBuilder where = new BooleanBuilder();
    if (filter != null) {
        if (filter.getCategoryId() != null) where.and(t.userCategory.id.eq(filter.getCategoryId()));
        if (filter.getMinAmount() != null) where.and(t.amount.goe(filter.getMinAmount()));
        if (filter.getMaxAmount() != null) where.and(t.amount.loe(filter.getMaxAmount()));
        if (filter.getDateFrom() != null) where.and(t.date.goe(filter.getDateFrom().atStartOfDay()));
        if (filter.getDateTo() != null) where.and(t.date.loe(filter.getDateTo().atTime(23, 59, 59)));
    }


    return queryFactory
            .select(Projections.constructor(TransactionExpenseDto.class,
                    t.userCategory.name,
                    t.amount.sum(),
                    t.defaultCategory.id,
                    t.userCategory.id,
                    t.date.max()
            ))
            .from(t)
            .where(where)
            .groupBy(
                    t.userCategory.name,
                    t.defaultCategory.id,
                    t.userCategory.id
            )
            .fetch();
}

}




