package com.example.finalTask.dto.user;

import com.example.finalTask.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private String email;
    private UserRole role;
}