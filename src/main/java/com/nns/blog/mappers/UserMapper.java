package com.nns.blog.mappers;

import com.nns.blog.dto.common.UserDto;
import com.nns.blog.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDto userDto);
}
