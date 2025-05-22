package com.example.finalTask.serivces;

import com.example.finalTask.dto.user.UserRequestDto;
import com.example.finalTask.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto findById(Long id);
    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto update(Long id, UserRequestDto userRequestDto);
    void delete(Long id);

}
