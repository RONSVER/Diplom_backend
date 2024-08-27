package com.superstore.services;

import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO createUser(UserDTO user, String hexPassword);
    UserDTO updateUser(Long id, UserDTO user);
    UserDTO findById(Long id);
    UserDTO findByEmail(String email);
    UserRegisterDTO registerUser(UserRegisterDTO user, String hexPassword);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    void deleteById(Long id);
}
