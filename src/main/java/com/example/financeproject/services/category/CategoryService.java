package com.example.financeproject.services.category;

import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.dto.dtoCategory.GetCategoryDto;
import com.example.financeproject.dto.dtoCategory.UpdateCategoryDto;


import java.util.List;

public interface CategoryService {
     CategoryDto addCategoryToAccount(CategoryDto categoryDto);
     List<GetCategoryDto> getAllCategories(Long accountId);
     void deleteCategoryFromAccount(Long categoryId);
     UpdateCategoryDto updateCategory(Long categoryId,  UpdateCategoryDto categoryDto);
}
