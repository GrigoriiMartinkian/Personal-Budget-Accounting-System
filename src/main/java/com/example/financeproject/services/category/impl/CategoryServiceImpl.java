package com.example.financeproject.services.category.impl;

import com.example.financeproject.dto.CategoryDto;
import com.example.financeproject.mappers.CategoryMapper;
import com.example.financeproject.models.Account;
import com.example.financeproject.models.Category;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.category.CategoryRepository;
import com.example.financeproject.services.category.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    private final AccountRepository accountRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, AccountRepository accountRepository) {
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryDto addCategoryToAccount(CategoryDto categoryDto) {
        Account account = accountRepository.findById(categoryDto.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Category category = new Category(categoryDto.getName(), categoryDto.getType(), account);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    @Transactional
    public CategoryDto editCategoryFromAccount(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        category.setName(categoryDto.getName());
        category.setType(categoryDto.getType());
        Category editCategory =categoryRepository.save(category);
       return  categoryMapper.toDto(editCategory);
    }

    @Transactional
    public void deleteUserCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }


}

