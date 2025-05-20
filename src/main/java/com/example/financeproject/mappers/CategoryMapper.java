package com.example.financeproject.mappers;
import com.example.financeproject.dto.CategoryDto;
import com.example.financeproject.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "account.id", target = "accountId")
    CategoryDto toDto(Category category) ;


    @Mapping(source = "accountId", target = "account.id")
    Category toEntity(CategoryDto dto);
}
