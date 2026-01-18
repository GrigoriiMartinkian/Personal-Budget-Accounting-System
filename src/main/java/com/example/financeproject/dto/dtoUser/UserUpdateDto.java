package com.example.financeproject.dto.dtoUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
