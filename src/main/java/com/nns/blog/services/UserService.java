package com.nns.blog.services;

import com.nns.blog.dto.common.UserDto;
import com.nns.blog.dto.responses.UserDtoRp;

import java.util.List;

public interface UserService {
    UserDtoRp registerUser(UserDto userDto);
    UserDtoRp createUser(UserDto user);
    UserDtoRp updateUser(UserDto user, Long userId);
    UserDtoRp getUserById(Long userId);
    List<UserDtoRp> getAllUsers();
    void deleteUser(Long userId);
}
