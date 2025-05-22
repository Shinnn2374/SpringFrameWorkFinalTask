package com.example.finalTask.serivces;

import com.example.finalTask.dto.user.UserRequestDto;
import com.example.finalTask.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto findById(long id);
    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto update(long id, UserRequestDto userRequestDto);
    void delete(long id);
    List<UserResponseDto> findAll();
    UserResponseDto findByUsername(String username);
}