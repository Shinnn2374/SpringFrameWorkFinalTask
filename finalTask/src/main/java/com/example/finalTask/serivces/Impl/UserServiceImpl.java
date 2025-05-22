package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.user.UserRequestDto;
import com.example.finalTask.dto.user.UserResponseDto;
import com.example.finalTask.entity.User;
import com.example.finalTask.exception.BadRequestException;
import com.example.finalTask.exception.NotFoundException;
import com.example.finalTask.mapper.UserMapper;
import com.example.finalTask.repository.UserRepository;
import com.example.finalTask.serivces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsernameOrEmail(userRequestDto.getUsername(), userRequestDto.getEmail())) {
            throw new BadRequestException("Username or email already exists");
        }

        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        if (!user.getUsername().equals(userRequestDto.getUsername()) {
            throw new BadRequestException("Username cannot be changed");
        }

        if (!user.getEmail().equals(userRequestDto.getEmail()) &&
                userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        userMapper.updateEntityFromDto(userRequestDto, user);
        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}