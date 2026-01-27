package com.example.financeproject.validation.CategoryValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryXorValidator.class)
public @interface ValidCategorySelection {

    String message() default
            "Either defaultCategoryId or categoryId must be provided, but not both";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

