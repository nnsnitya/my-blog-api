package com.nns.blog.dto.common;

import com.nns.blog.entities.Role;

public record RoleDto(
        Long id,
        String name
) {
    public static RoleDto from(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }
}
