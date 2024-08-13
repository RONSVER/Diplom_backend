package com.superstore.dto;

import com.superstore.entity.User;

public record UserCreateDTO(
        String name,
        String email,
        String phoneNumber,
        String passwordHash,
        User.Role role
) {
}