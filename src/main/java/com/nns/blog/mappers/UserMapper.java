package com.nns.blog.mappers;

import com.nns.blog.dto.common.UserDto;
import com.nns.blog.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
