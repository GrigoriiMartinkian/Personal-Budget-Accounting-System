package com.example.financeproject.validation.CategoryValidators;

import com.example.financeproject.dto.dtoTransaction.TransactionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryXorValidator
        implements ConstraintValidator<ValidCategorySelection, TransactionDto> {

    @Override
    public boolean isValid(TransactionDto dto,
                           ConstraintValidatorContext context) {

        if (dto == null) {
            return true;
        }

        boolean hasDefault = dto.getDefaultCategoryId() != null;
        boolean hasCustom = dto.getCategoryId() != null;

        return hasDefault ^ hasCustom;
    }
}