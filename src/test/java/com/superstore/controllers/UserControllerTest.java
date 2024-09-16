package com.superstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superstore.config.WebSecurityConfig;
import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.entity.User;
import com.superstore.security.AuthenticationService;
import com.superstore.security.JwtService;
import com.superstore.security.model.JwtAuthenticationResponse;
import com.superstore.security.model.SignInRequest;
import com.superstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"Administrator", "Client"})
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testFindAll() throws Exception {
        UserDTO user1 = UserDTO.builder()

                .userId(1L)
                .name("User1")
                .email("test@gmail.com")
                .phoneNumber("+49-11111111111")
                .passwordHash("HEX-PASS")
                .role(User.Role.Client)
                .build();
        UserDTO user2 = UserDTO.builder()
                .userId(2L)
                .name("User2")
                .email("test2@gmail.com")
                .phoneNumber("+49-11111111111")
                .passwordHash("HEX-PASS")
                .role(User.Role.Client)
                .build();

        List<UserDTO> users = Arrays.asList(user1, user2);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(users.size()));
    }

    @Test
    public void testFindById() throws Exception {
        UserDTO user = UserDTO.builder()
                .userId(1L)
                .name("User1")
                .email("test@gmail.com")
                .phoneNumber("+49-11111111111")
                .passwordHash("HEX-PASS")
                .role(User.Role.Client)
                .build();

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L));
    }

    @Test
    public void testSave() throws Exception {
        UserDTO user = UserDTO.builder()
                .userId(3L)
                .name("User3")
                .email("test3@gmail.com")
                .phoneNumber("+49-33333333333")
                .passwordHash("HEX-PASS")
                .role(User.Role.Client)
                .build();

        when(passwordEncoder.encode(any())).thenReturn("ENCODED-PASSWORD");
        when(userService.createUser(user, "ENCODED-PASSWORD")).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        String userJsonString = mapper.writeValueAsString(user);

        logger.info(userJsonString);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonString))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(3L));
    }
    @Test
    public void testUpdateUser() throws Exception {
        UserDTO user = UserDTO.builder()
                .userId(1L)
                .name("User1Update")
                .email("testupdate@gmail.com")
                .phoneNumber("+49-11111111111")
                .passwordHash("HEX-PASS")
                .role(User.Role.Client)
                .build();

        when(userService.updateUser(1L, user)).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        String userJsonString = mapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonString))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User1Update"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User1Update"));
    }

    @Test
    public void testDeleteById() throws Exception {
        Long id = 1L;

        doNothing().when(userService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/users/" + id))
                .andExpect(status().isOk());
    }
    @Test
    public void testRegisterUser() throws Exception {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "TestUser",
                "testuser@gmail.com",
                "+49-55555555555",
                "HEX-PASS");

        when(passwordEncoder.encode(any())).thenReturn("ENCODED-PASSWORD");
        when(userService.registerUser(userRegisterDTO, "ENCODED-PASSWORD")).thenReturn(userRegisterDTO);

        ObjectMapper mapper = new ObjectMapper();
        String userRegisterJsonString = mapper.writeValueAsString(userRegisterDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegisterJsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void testLogin() throws Exception {
        SignInRequest request = new SignInRequest();
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();

        when(authenticationService.authenticate(request)).thenReturn(response);

        ObjectMapper mapper = new ObjectMapper();
        String requestJsonString = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonString))
                .andExpect(status().isOk());
    }


}
