package com.nns.blog.dto.common;

import com.nns.blog.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDto(
      Long id,
      @NotEmpty
      @Size(min = 4, message = "Username must be min of 4 characters!!")
      String name,
      @Email
      String email,
      @NotEmpty
      @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars!!")
      String password,
      String about,
      Set<RoleDto> roles
) {

    public static UserDto from(User user) {
        Set<RoleDto> roleDtos = user.getRoles().stream().map(RoleDto::from).collect(Collectors.toSet());
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAbout(),
                roleDtos
        );
    }
}
