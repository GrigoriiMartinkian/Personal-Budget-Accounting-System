package com.example.financeproject.services.user;

import com.example.financeproject.dto.dtoUser.UserDto;
import com.example.financeproject.dto.dtoUser.UserReturnDto;
import com.example.financeproject.dto.dtoUser.UserUpdateDto;
import com.example.financeproject.dto.dtoUser.VerifyDto;
import com.example.financeproject.exception.ResourceNotFoundException;
import com.example.financeproject.mappers.UserMapper;
import com.example.financeproject.models.User;
import com.example.financeproject.repositories.user.UserRepository;
import com.example.financeproject.security.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public UserReturnDto addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserReturnDto(userRepository.save(user));
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        return userMapper.toDto(user);
    }

    @Transactional
    public UserUpdateDto updateUser(Long id, UserUpdateDto userUpdate) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new  ResourceNotFoundException("User Not Found"));

        if (userUpdate.getUsername() != null) {
            user.setUsername(userUpdate.getUsername());
        }
        if (userUpdate.getPassword() != null) {
            user.setPassword(userUpdate.getPassword());
        }
        if (userUpdate.getEmail() != null) {
            user.setEmail(userUpdate.getEmail());
        }
        return userMapper.toUpdateDto(userRepository.save(user));

    }

    public String verify(VerifyDto userDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDto.getUsername());
        }
        return "Authentication Failed";
    }


}

