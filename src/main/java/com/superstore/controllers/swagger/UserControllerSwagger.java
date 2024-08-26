package com.superstore.controllers.swagger;

import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.security.model.JwtAuthenticationResponse;
import com.superstore.security.model.SignInRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserControllerSwagger {

    ResponseEntity<List<UserDTO>> findAll();


    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    ResponseEntity<UserDTO> findById(@Parameter(
                                             name = "id",
                                             description = "Идентификатор пользователя",
                                             required = true,
                                             in = ParameterIn.PATH
                                     )
                                     Long id);

    ResponseEntity<UserDTO> save(UserDTO userCreateDTO);

    @Operation(
            summary = "Обновляет пользователя по ID",
            description = "Обновляет пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    ResponseEntity<UserDTO> updateUser(@Parameter(
                                               name = "id",
                                               description = "Идентификатор пользователя",
                                               required = true,
                                               in = ParameterIn.PATH
                                       )
                                       Long id, UserDTO userDTO);

    @Operation(
            summary = "Удалить пользователя по ID",
            description = "Удаляет пользователя на основе переданного идентификатора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    ResponseEntity<Void> deleteById(@Parameter(
                                            name = "id",
                                            description = "Идентификатор пользователя",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                                    Long id);
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Аутентифицирует пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
            }
    )
    ResponseEntity<JwtAuthenticationResponse> login(@Parameter(
                                                            name = "id",
                                                            description = "Идентификатор пользователя",
                                                            required = true,
                                                            in = ParameterIn.PATH
                                                    )
                                                    SignInRequest request);

    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрирует пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь создан"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    ResponseEntity<Void> registerUser(UserRegisterDTO userCreateDTO);
}
