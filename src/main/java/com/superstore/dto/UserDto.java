package com.superstore.dto;

import javax.management.relation.Role;

public record UserDto(Long userId, String name, String email, String phoneNumber, Role role) {
}
