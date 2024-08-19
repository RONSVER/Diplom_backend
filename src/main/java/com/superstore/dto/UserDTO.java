package com.superstore.dto;

import com.superstore.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель пользователя")
public record UserDTO(
        @Schema(description = "Идентификатор пользователя", example = "1")
        Long userId,

        @Schema(description = "Имя пользователя", example = "John")
        String name,

        @Schema(description = "Email пользователя", example = "John@gmail.com")
        String email,

        @Schema(description = "Телефон пользователя", example = "+49-111-111-111")
        String phoneNumber,

        @Schema(description = "Роль пользователя", example = "Administrator или Client")
        User.Role role
) {
}
