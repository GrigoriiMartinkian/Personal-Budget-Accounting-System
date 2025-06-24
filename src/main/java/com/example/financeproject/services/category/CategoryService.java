package com.example.financeproject.services.category;

import com.example.financeproject.dto.CategoryDto;

public interface CategoryService {
     CategoryDto addCategoryToAccount(CategoryDto categoryDto);
     CategoryDto editCategoryFromAccount(Long categoryId, CategoryDto categoryDto);
     void deleteUserCategory(Long categoryId);
}
