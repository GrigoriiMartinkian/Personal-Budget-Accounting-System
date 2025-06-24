package com.example.financeproject.dto;
import com.example.financeproject.models.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private CategoryType type; // Указываем, доход это или расход

    private Long accountId;

}
