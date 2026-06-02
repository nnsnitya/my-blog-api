package com.nns.blog.services.impl;

import com.nns.blog.constants.AppConstants;
import com.nns.blog.dto.common.UserDto;
import com.nns.blog.entities.Role;
import com.nns.blog.entities.User;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.repositories.RoleRepository;
import com.nns.blog.repositories.UserRepository;
import com.nns.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UserDto registerUser(UserDto userDto) {
        Set<Role> roles = new HashSet<>();//as role is not initialized in UserDto
        User user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .about(userDto.about())
                .roles(roles)           //because UserDto is record not class
                .build();
        Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = userRepo.save(user);
        return UserDto.from(newUser);
    }

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

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.getRoles().clear();
        userRepo.delete(user);
    }

}
