package com.superstore.dto;

import com.superstore.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 4, message = "Name must be at least 4 characters long")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String passwordHash,

        @NotNull(message = "Role is required")
        User.Role role
) {
}