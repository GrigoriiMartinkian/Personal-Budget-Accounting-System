package com.example.financeproject.dto.dtoCategory;

import com.example.financeproject.models.CategoryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCategoryDto {

    private Long id;

    private String name;

    private CategoryType type;

}
