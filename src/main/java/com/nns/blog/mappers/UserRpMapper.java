package com.nns.blog.mappers;

import com.nns.blog.dto.responses.UserDtoRp;
import com.nns.blog.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRpMapper {
    UserDtoRp toDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDtoRp userDtoRp);
}
