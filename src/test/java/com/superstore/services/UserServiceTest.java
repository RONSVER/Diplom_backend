package com.superstore.services;

import com.superstore.dto.UserDTO;
import com.superstore.entity.User;
import com.superstore.exceptions.NoUniqueUserEmailException;
import com.superstore.mapper.UserMapper;
import com.superstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        // Arrange

        User user = new User();
        user.setUserId(1L);
        user.setName("John");
        user.setEmail("John@gmail.com");
        user.setPhoneNumber("49-111-111-111");
        user.setPasswordHash("hashedPassword");
        user.setRole(User.Role.Client);

        List<User> users = List.of(user);
        List<UserDTO> userDTOs = List.of(new UserDTO(1L, "John", "John@gmail.com", "+49-111-111-111", "hashedPassword", User.Role.Client));

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.userToUserDTO(users.get(0))).thenReturn(userDTOs.get(0));

        // Act
        List<UserDTO> result = userService.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).name());
    }

    @Test
    public void testFindById_UserExists() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        user.setName("John");
        user.setEmail("John@gmail.com");
        user.setPhoneNumber("49-111-111-111");
        user.setPasswordHash("hashedPassword");
        user.setRole(User.Role.Client);
        UserDTO userDTO = new UserDTO(1L, "John", "John@gmail.com", "+49-111-111-111", "hashedPassword", User.Role.Client);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.name());
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        UserDTO userDTO = new UserDTO(null, "John", "John@gmail.com", "+49-111-111-111", "plainPassword", User.Role.Client);
        User user = new User();
        user.setName("John");
        user.setEmail("John@gmail.com");
        user.setPhoneNumber("49-111-111-111");
        user.setPasswordHash("hashedPassword");
        user.setRole(User.Role.Client);

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setName("John");
        savedUser.setEmail("John@gmail.com");
        savedUser.setPhoneNumber("49-111-111-111");
        savedUser.setPasswordHash("hashedPassword");
        savedUser.setRole(User.Role.Client);


        when(userMapper.userDTOToUser(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.userToUserDTO(savedUser)).thenReturn(new UserDTO(1L, "John", "John@gmail.com", "+49-111-111-111", "hashedPassword", User.Role.Client));

        // Act
        UserDTO result = userService.createUser(userDTO, "hashedPassword");

        // Assert
        assertNotNull(result);
        assertEquals("John", result.name());
        assertEquals(1L, result.userId());
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO(null, "John", "John@gmail.com", "+49-111-111-111", "plainPassword", User.Role.Client);

        when(userRepository.existsByEmail("John@gmail.com")).thenReturn(true);

        // Act
        assertThrows(NoUniqueUserEmailException.class, () -> {
            userService.createUser(userDTO, "hashedPassword");
        });
    }
}
