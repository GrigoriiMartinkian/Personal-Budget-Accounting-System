package com.example.financeproject.services.user;

import com.example.financeproject.dto.dtoUser.UserDto;
import com.example.financeproject.dto.dtoUser.UserReturnDto;
import com.example.financeproject.dto.dtoUser.UserUpdateDto;
import com.example.financeproject.dto.dtoUser.VerifyDto;
import com.example.financeproject.models.User;

public interface UserService {
    UserDto findUserById(Long id);

    UserReturnDto addUser(UserDto userDto);
    UserDto getUserById(Long id);
    UserUpdateDto updateUser(Long id, UserUpdateDto user);
    String verify(VerifyDto userDto);

}
