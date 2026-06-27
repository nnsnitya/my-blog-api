package com.nns.blog.mappers;

import com.nns.blog.dto.common.RoleDto;
import com.nns.blog.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

}
