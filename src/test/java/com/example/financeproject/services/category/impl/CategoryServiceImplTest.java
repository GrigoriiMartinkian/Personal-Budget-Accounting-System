package com.example.financeproject.services.category.impl;

import com.example.financeproject.dto.dtoAccount.AccountDto;
import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.mappers.AccountMapper;
import com.example.financeproject.mappers.CategoryMapper;
import com.example.financeproject.models.*;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.category.CategoryRepository;
import com.example.financeproject.repositories.transaction.AccountTransactionRepository;
import com.example.financeproject.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.financeproject.models.AccountType.CASH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper accountMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountTransactionRepository accountTransactionRepository;
    @Mock
    private  CategoryMapper categoryMapper;
    @Mock
    private  AccountRepository accountRepository;


    @Test
    void addCategoryToAccount_success() {

        Long accountId = 1L;

        Account account = new Account();
        account.setId(accountId);

        CategoryDto categoryDto = CategoryDto.builder()
                .name("Gambling")
                .type(CategoryType.EXPENSE)
                .accountId(accountId)
                .build();

        Category savedCategory = Category.builder()
                .name("Gambling")
                .type(CategoryType.EXPENSE)
                .account(account)
                .build();

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(savedCategory);

        when(categoryMapper.toDto(any(Category.class)))
                .thenReturn(categoryDto);

        CategoryDto result = categoryService.addCategoryToAccount(categoryDto);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());

        Category captured = captor.getValue();

        assertEquals("Gambling", captured.getName());
        assertEquals(CategoryType.EXPENSE, captured.getType());
        assertEquals(account, captured.getAccount());

        assertEquals(categoryDto, result);
    }

    @Test
    void shouldThrowException_whenAccountNotFound(){
        Long accountId = 1L;
        CategoryDto categoryDto= CategoryDto
                .builder()
                .name("Gambling")
                .type(CategoryType.EXPENSE)
                .accountId(accountId)
                .build();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                categoryService.addCategoryToAccount(categoryDto)
        );
    }





}