package com.superstore.mapper;

import com.superstore.dto.UserDto;
import com.superstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "userId", source = "entity.userId"),
            @Mapping(target = "name", source = "entity.name"),
            @Mapping(target = "email", source = "entity.email"),
            @Mapping(target = "phoneNumber", source = "entity.phoneNumber"),
            @Mapping(target = "role", source = "entity.role"),
    })
    UserDto hotelToHotelListViewDTO(User entity);
}
