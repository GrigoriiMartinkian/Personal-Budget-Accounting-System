package com.example.financeproject.controller;
import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.security.JWTService;
import com.example.financeproject.services.category.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import static com.example.financeproject.models.CategoryType.EXPENSE;


@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {
    @MockBean
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void successAddCategory() throws Exception {
       CategoryDto categoryDto =  CategoryDto
               .builder()
               .name("Gambling")
               .type(EXPENSE)
               .accountId(1L)
               .build();
    }

}