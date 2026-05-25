package com.nns.blog.services.impl;

import com.nns.blog.dto.common.UserDto;
import com.nns.blog.entities.User;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.repositories.UserRepository;
import com.nns.blog.services.UserService;
import com.nns.blog.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = Mapper.mapToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return Mapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setAbout(userDto.about());

        User updatedUser = userRepo.save(user);
        UserDto userDto1 = Mapper.mapToUserDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return Mapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> Mapper.mapToUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepo.delete(user);
    }

}
