package com.example.financeproject.controller;
import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.dto.dtoCategory.GetCategoryDto;
import com.example.financeproject.dto.dtoCategory.UpdateCategoryDto;
import com.example.financeproject.services.category.CategoryService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategoryToAccount(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping("/get_all/{accountId}")
    public ResponseEntity<List<GetCategoryDto>> getAllCategories(@PathVariable Long accountId){
        List<GetCategoryDto> dto=categoryService.getAllCategories(accountId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @DeleteMapping("/delete_category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId ){
         categoryService.deleteCategoryFromAccount(categoryId);
         return ResponseEntity.ok("category successfully deleted");
    }

    @PatchMapping("/update_category/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryDto categoryDto) {
       categoryService.updateCategory(categoryId, categoryDto);
       return ResponseEntity.ok("category successfully updated");
    }





}


