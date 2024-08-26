package com.superstore.controllers;

import com.superstore.dto.UserCreateDTO;
import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.entity.User;
import com.superstore.mapper.UserMapper;
import com.superstore.security.AuthenticationService;
import com.superstore.security.model.JwtAuthenticationResponse;
import com.superstore.security.model.SignInRequest;
import com.superstore.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = service.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<UserDTO> findById(@PathVariable
                                            @Parameter(
                                                    name = "id",
                                                    description = "Идентификатор пользователя",
                                                    required = true,
                                                    in = ParameterIn.PATH
                                            )
                                            Long id) {
        return ResponseEntity.ok(userMapper.userToUserDTO(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = userMapper.userCreateDTOToUser(userCreateDTO);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        UserDTO savedUser = userMapper.userToUserDTO(service.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(
            summary = "Обновляет пользователя по ID",
            description = "Обновляет пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable
                                              @Parameter(
                                                      name = "id",
                                                      description = "Идентификатор пользователя",
                                                      required = true,
                                                      in = ParameterIn.PATH
                                              )
                                              Long id, @RequestBody UserDTO userDTO) {
        User existingUser = service.findById(id);

        User updatedUserEntity = userMapper.userDTOToUser(userDTO);

        existingUser.setName(updatedUserEntity.getName());
        existingUser.setEmail(updatedUserEntity.getEmail());
        existingUser.setPhoneNumber(updatedUserEntity.getPhoneNumber());
        existingUser.setRole(updatedUserEntity.getRole());

        UserDTO updatedUser = userMapper.userToUserDTO(service.save(existingUser));

        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "Удалить пользователя по ID",
            description = "Удаляет пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable
                                           @Parameter(
                                                   name = "id",
                                                   description = "Идентификатор пользователя",
                                                   required = true,
                                                   in = ParameterIn.PATH
                                           )
                                           Long id) {
        if (service.existsById(id)) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Аутентифицирует пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody
                                                           @Parameter(
                                                                   name = "id",
                                                                   description = "Идентификатор пользователя",
                                                                   required = true,
                                                                   in = ParameterIn.PATH
                                                           )
                                                           SignInRequest request) {
        JwtAuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрирует пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь создан"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDTO userCreateDTO) {
        if (service.existsByEmail(userCreateDTO.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use: " + userCreateDTO.email());
        }

        User user = userMapper.userRegisterDTOToUser(userCreateDTO);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(User.Role.Client);
        service.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("You have been registered successfully!");
    }
}
