package com.superstore.controllers;

import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
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

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
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
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createUser(userCreateDTO, passwordEncoder.encode(userCreateDTO.passwordHash())));
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

        UserDTO updatedUser = service.updateUser(id ,userDTO);
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
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
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
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterDTO userCreateDTO) {

        service.registerUser(userCreateDTO, passwordEncoder.encode(userCreateDTO.passwordHash()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
