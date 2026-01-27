package com.example.financeproject.dto.dtoCategory;
import com.example.financeproject.models.CategoryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {

    @NotBlank(message = "name must exist ")
    @Size(min = 1, max = 30, message = "The username must contain between 6 and 30 characters.")
    private String name;

    @NotBlank
    private CategoryType type;

    @NotNull
    private Long accountId;

}
