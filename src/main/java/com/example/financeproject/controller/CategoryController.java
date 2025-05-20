package com.example.financeproject.controller;
import com.example.financeproject.dto.CategoryDto;
import com.example.financeproject.services.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategoryToAccount(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> editCategory(
            @PathVariable Long id, @RequestBody CategoryDto categoryDto) {

            CategoryDto updatedCategory = categoryService.editCategoryFromAccount(id, categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);

    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteUserCategory(@PathVariable Long Id) {
        categoryService.deleteUserCategory(Id);
        return ResponseEntity.ok("Категория успешно удалена");
    }


}


