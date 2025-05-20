package com.example.financeproject.dto;
import com.example.financeproject.models.CategoryType;
import lombok.Data;


@Data
public class CategoryDto {

    private Long id;

    private String name;

    private CategoryType type; // Указываем, доход это или расход

    private Long accountId;

}
