package com.superstore.dto;

import com.superstore.entity.User;

public record UserDto(
        Long userId,
        String name,
        String email,
        String phoneNumber,
        User.Role role
) {
}
