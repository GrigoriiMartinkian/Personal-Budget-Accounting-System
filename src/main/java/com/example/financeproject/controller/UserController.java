package com.example.financeproject.controller;

import com.example.financeproject.dto.dtoUser.UserDto;
import com.example.financeproject.dto.dtoUser.UserReturnDto;
import com.example.financeproject.dto.dtoUser.UserUpdateDto;
import com.example.financeproject.dto.dtoUser.VerifyDto;
import com.example.financeproject.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserReturnDto> register(@Valid @RequestBody UserDto userDto) {
      return ResponseEntity.status(HttpStatus.CREATED).
              body(userService.addUser(userDto));
    }

    @PostMapping("/login")
    public String login(@RequestBody VerifyDto userDto) {
        return userService.verify(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        //System.out.println("Mapped user: " + userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UserUpdateDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdate) {
        UserUpdateDto userDto = userService.updateUser(id, userUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
