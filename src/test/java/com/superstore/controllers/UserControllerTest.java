package com.superstore.controllers;

import com.superstore.dto.UserDTO;
import com.superstore.entity.User;
import com.superstore.security.AuthenticationService;
import com.superstore.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "admin", authorities = { "Administrator", "Client" })
    public void testFindAll() throws Exception {
        // Arrange
        List<UserDTO> userDTOs = List.of(new UserDTO(1L, "John", "John@gmail.com", "+49-111-111-111", "hashedPassword", User.Role.Client));
        when(userService.findAll()).thenReturn(userDTOs);

        // Act & Assert
        mockMvc.perform(get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "Administrator", "Client" })
    public void testFindById_UserExists() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(1L, "John", "John@gmail.com", "+49-111-111-111", "hashedPassword", User.Role.Client);
        when(userService.findById(1L)).thenReturn(userDTO);

        // Act & Assert
        mockMvc.perform(get("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void testSaveUser_Success() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(null, "John", "John@gmail.com", "+49-111-111-111", "plainPassword", User.Role.Client);
        UserDTO savedUserDTO = new UserDTO(1L, "John", "John@gmail.com", "+49-111-111-111", "hashedPassword", User.Role.Client);

        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword");
        when(userService.createUser(any(UserDTO.class), eq("hashedPassword"))).thenReturn(savedUserDTO);

        // Act & Assert
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John\", \"email\": \"John@gmail.com\", \"phoneNumber\": \"+49-111-111-111\", \"passwordHash\": \"plainPassword\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "Administrator", "Client" })

    public void testDeleteById_UserExists() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteById(1L);
    }
}
