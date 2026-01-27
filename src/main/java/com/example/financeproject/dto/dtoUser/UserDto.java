package com.example.financeproject.dto.dtoUser;

import com.example.financeproject.validation.UniqueValidators.email.UniqueEmail;
import com.example.financeproject.validation.UniqueValidators.username.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long userId;

    @NotBlank
    @UniqueUsername
    private String username;

    @NotBlank(message = "Email must exist")
    @UniqueEmail
    @Email(message = "Incorrect format email")
    private String email;

    @NotBlank
    @Size(min = 6, max = 30, message = "The password must contain between 6 and 30 characters.")
//    @Pattern(
//            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
//            message = "Password must contain number, letters in both registers and special symbol(e.g @,&,# etc)"
//    )
    private String password;

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
