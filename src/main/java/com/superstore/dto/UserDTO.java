package com.superstore.dto;

import com.superstore.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(description = "Модель пользователя")
@Builder
public record UserDTO(
        @Schema(description = "Идентификатор пользователя", example = "1")
        Long userId,

        @Schema(description = "Имя пользователя", example = "John")
        @NotBlank(message = "Name is required")
        @Size(min = 4, message = "Name must be at least 4 characters long")
        String name,

        @Schema(description = "Email пользователя", example = "John@gmail.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "Телефон пользователя", example = "+49-111-111-111")
        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String passwordHash,

        @Schema(description = "Роль пользователя", example = "Administrator или Client")
        User.Role role
) {
}
