package com.nns.blog.dto.common;

public record UserDto(
      Long id,
      String name,
      String email,
      String password,
      String about
) {
}
