package com.example.financeproject.mappers;
import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.dto.dtoCategory.GetCategoryDto;
import com.example.financeproject.dto.dtoCategory.UpdateCategoryDto;
import com.example.financeproject.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface CategoryMapper {
    @Mapping(source = "account.id", target = "accountId")
    CategoryDto toDto(Category category) ;


    @Mapping(source = "accountId", target = "account.id")
    Category toEntity(CategoryDto dto);

    List<GetCategoryDto> toGetCategoryDtoList(List<Category> categoryList);

    UpdateCategoryDto toUpdateCategoryDto(Category category);


}
