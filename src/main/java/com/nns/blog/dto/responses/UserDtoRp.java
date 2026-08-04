package com.nns.blog.dto.responses;

import com.nns.blog.dto.common.RoleDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDtoRp(
      Long id,
      @NotEmpty
      @Size(min = 4, message = "Username must be min of 4 characters!!")
      String name,
      @Email
      String email,
      String about,
      Set<RoleDto> roles
) {

}
