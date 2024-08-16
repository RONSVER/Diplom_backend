package com.superstore.controllers;

import com.superstore.dto.UserCreateDTO;
import com.superstore.dto.UserDTO;
import com.superstore.entity.User;
import com.superstore.exceptions.UserNotFoundException;
import com.superstore.mapper.UserMapper;
import com.superstore.security.AuthenticationService;
import com.superstore.security.model.JwtAuthenticationResponse;
import com.superstore.security.model.SignInRequest;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    private List<UserDTO> findAll() {
        return userService.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList()
                );
    }

    @GetMapping("/{id}")
    private UserDTO findById(@PathVariable Long id) {
        return userMapper
                .userToUserDTO(
                        userService.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("No user with id " + id))
                );
    }

    @PostMapping
    private UserDTO save(@RequestBody UserCreateDTO userCreateDTO) {
        User user = userMapper.userCreateDTOToUser(userCreateDTO);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userMapper
                .userToUserDTO(userService.save(user));
    }

    @PutMapping("/{id}")
    private UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        if (!userService.findById(id).isPresent()) {
            return null;
        }

        User byId = userService.findById(id).get();
        byId.setName(userDTO.name());
        byId.setEmail(userDTO.email());
        byId.setPhoneNumber(userDTO.phoneNumber());
        byId.setRole(userDTO.role());
        return userMapper.userToUserDTO(userService.save(byId));
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody SignInRequest request) {
        return authenticationService.authenticate(request);
    }
}
