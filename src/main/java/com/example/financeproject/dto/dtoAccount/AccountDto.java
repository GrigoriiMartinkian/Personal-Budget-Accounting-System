package com.example.financeproject.dto.dtoAccount;

import com.example.financeproject.dto.dtoCategory.CategoryDto;
import com.example.financeproject.models.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    @NotNull(message = "Id must exist ")
    @Min(value = 1, message = "The value must be non-negative")
    private Long userId;

    @NotBlank(message = "name must exist ")
    @Size(min = 6, max = 30, message = "The username must contain between 6 and 30 characters.")
    private String name;

    @Min(value = 0, message = "The value must be non-negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal balance;

    @NotBlank(message = "Account type is required")
    private AccountType type;

    @NotBlank(message = "Currency code is required")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency code must be 3 uppercase letters (e.g. PLN, EUR)"
    )
    private String code;

    @NotNull(message = "Exchange rate is required")
    @DecimalMin(value = "0.000001", inclusive = true,
            message = "Exchange rate must be greater than 0")
    private BigDecimal exchangeRate;

    private List<CategoryDto> categories = new ArrayList<>();


}


