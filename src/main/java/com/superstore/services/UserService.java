package com.superstore.services;

import com.superstore.dto.UserCreateDTO;
import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    UserRegisterDTO registerUser(UserRegisterDTO user);
    UserDTO createUser(UserCreateDTO user);
    UserDTO updateUser(Long id, UserDTO user);
    void deleteById(Long id);
}
