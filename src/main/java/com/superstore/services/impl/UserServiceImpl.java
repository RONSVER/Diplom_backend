package com.superstore.services.impl;

import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.entity.User;
import com.superstore.exceptions.NoUniqueUserEmailException;
import com.superstore.exceptions.UserNotFoundException;
import com.superstore.mapper.UserMapper;
import com.superstore.repository.UserRepository;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository dao;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> findAll() {
        return dao.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    private User createAndSaveUser(String email, User user, String hexPassword) {
        if (existsByEmail(email)) {
            throw new NoUniqueUserEmailException("Email is already in use: " + email);
        }
        user.setPasswordHash(hexPassword);
        return dao.save(user);
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
    public UserDTO findByEmail(String email) {
        return dao.findByEmail(email).map(userMapper::userToUserDTO)
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
    public UserRegisterDTO registerUser(UserRegisterDTO userRegisterDTO, String hexPassword) {
        User user = userMapper.userRegisterDTOToUser(userRegisterDTO);
        user.setRole(User.Role.Client);  // Установка роли только для регистрации
        createAndSaveUser(userRegisterDTO.email(), user, hexPassword);
        return userRegisterDTO;
    }

    @Override
    public UserDTO createUser(UserDTO userCreateDTO, String hexPassword) {
        User user = userMapper.userDTOToUser(userCreateDTO);
        User savedUser = createAndSaveUser(userCreateDTO.email(), user, hexPassword);
        return userMapper.userToUserDTO(savedUser);
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
        if (!existsById(id)) {
            logger.error("User with ID {} not found", id);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        dao.deleteById(id);
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            User user = dao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with Email " + email + " not found"));
            return user.getUserId();
        } else {
            throw new IllegalArgumentException("The primary authentication object cannot be used to obtain the ID");
        }
    }
}
