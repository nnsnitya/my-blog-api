package com.nns.blog.services.impl;

import com.nns.blog.dto.common.UserDto;
import com.nns.blog.entities.User;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.repositories.UserRepository;
import com.nns.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(this.passwordEncoder.encode(userDto.password()))
                .about(userDto.about())
                .build();
        User savedUser = this.userRepo.save(user);
        return UserDto.from(savedUser);
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
        return UserDto.from(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return UserDto.from(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> UserDto.from(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepo.delete(user);
    }

}
