package com.example.financeproject.controller;
import com.example.financeproject.dto.CategoryDto;
import com.example.financeproject.services.category.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategoryToAccount(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    @PutMapping("/edit_category/{id}")
    public ResponseEntity<CategoryDto> editCategory(
            @PathVariable Long id, @RequestBody CategoryDto categoryDto) {

            CategoryDto updatedCategory = categoryService.editCategoryFromAccount(id, categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);

    }

    @DeleteMapping("delete_category/{Id}")
    public ResponseEntity<String> deleteUserCategory(@PathVariable Long Id) {
        categoryService.deleteUserCategory(Id);
        return ResponseEntity.ok("Категория успешно удалена");
    }


}


