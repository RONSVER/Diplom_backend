package com.superstore.mapper;

import com.superstore.dto.UserDTO;
import com.superstore.dto.UserRegisterDTO;
import com.superstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
/*    @Mappings({
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "phoneNumber", source = "phoneNumber"),
            @Mapping(target = "role", source = "role"),
    })*/
    UserDTO userToUserDTO(User entity);

    User userDTOToUser(UserDTO userDTO);

    User userRegisterDTOToUser(UserRegisterDTO userRegisterDTO);
}
