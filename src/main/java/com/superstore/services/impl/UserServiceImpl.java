package com.superstore.services.impl;

import com.superstore.dto.UserCreateDTO;
import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.entity.User;
import com.superstore.exceptions.NoUniqueUserEmailException;
import com.superstore.exceptions.UserNotFoundException;
import com.superstore.mapper.UserMapper;
import com.superstore.repository.UserRepository;
import com.superstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository dao;
    private final UserMapper userMapper;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAll() {
        return dao.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return dao.findById(id).map(userMapper::userToUserDTO)
                .orElseThrow(() -> {
                    logger.error("User with ID {} not found", id);
                    return new UserNotFoundException("User with ID " + id + " not found");
                });
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with Email " + email + " not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return dao.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return dao.existsByUserId(id);
    }

    @Override
    public UserRegisterDTO registerUser(UserRegisterDTO userRegisterDTO) {
        if (existsByEmail(userRegisterDTO.email())) {
            throw new NoUniqueUserEmailException("Email is already in use: " + userRegisterDTO.email());
        }
        User user = userMapper.userRegisterDTOToUser(userRegisterDTO);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(User.Role.Client);
        dao.save(user);
        return userRegisterDTO;
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if (existsByEmail(userCreateDTO.email())) {
            throw new NoUniqueUserEmailException("Email is already in use: " + userCreateDTO.email());
        }
        User user = userMapper.userCreateDTOToUser(userCreateDTO);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userMapper.userToUserDTO(dao.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = dao.findById(id).orElseThrow(() ->  new UserNotFoundException("User with ID " + id + " not found"));
        User updatedUserEntity = userMapper.userDTOToUser(userDTO);

        existingUser.setName(updatedUserEntity.getName());
        existingUser.setEmail(updatedUserEntity.getEmail());
        existingUser.setPhoneNumber(updatedUserEntity.getPhoneNumber());
        existingUser.setRole(updatedUserEntity.getRole());
        return userMapper.userToUserDTO(dao.save(existingUser));
    }

    @Override
    public void deleteById(Long id) {
        if (existsById(id)) {
            logger.error("User with ID {} not found", id);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        dao.deleteById(id);
    }
}
