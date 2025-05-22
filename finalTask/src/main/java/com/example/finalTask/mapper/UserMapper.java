package com.example.finalTask.mapper;

import com.example.finalTask.dto.user.UserRequestDto;
import com.example.finalTask.dto.user.UserResponseDto;
import com.example.finalTask.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    void updateEntityFromDto(UserRequestDto userRequestDto, @MappingTarget User user);
}