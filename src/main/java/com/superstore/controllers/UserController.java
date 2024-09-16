package com.superstore.controllers;

import com.superstore.controllers.swagger.UserControllerSwagger;
import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.security.AuthenticationService;
import com.superstore.security.model.JwtAuthenticationResponse;
import com.superstore.security.model.SignInRequest;
import com.superstore.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing user-related operations.
 * This class handles the REST endpoints for CRUD operations related to users.
 *
 * @RestController annotation marks this class as a REST controller.
 * It is responsible for taking incoming HTTP requests and returning the appropriate HTTP responses.
 *
 * @RequestMapping annotation specifies the base URI path ("/v1/users") for all the endpoints in this class.
 *
 * @AllArgsConstructor annotation is used to generate a constructor with all the required arguments.
 *
 * Implements UserControllerSwagger interface which provides the documentation of the REST endpoints.
 */
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController implements UserControllerSwagger {

    private final UserService service;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userCreateDTO) {
        String encodedPassword = passwordEncoder.encode(userCreateDTO.passwordHash());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createUser(userCreateDTO, encodedPassword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = service.updateUser(id ,userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest request) {
        JwtAuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterDTO userCreateDTO) {
        String encodedPassword = passwordEncoder.encode(userCreateDTO.passwordHash());
        service.registerUser(userCreateDTO, encodedPassword);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
