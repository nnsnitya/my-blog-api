package com.nns.blog.controllers;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.common.UserDto;
import com.nns.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiConstants.VERSION1+"/users")
public class UserController {
    @Autowired
    private UserService userService;

    //POST - create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long uid) {
        userService.deleteUser(uid);
        return new ResponseEntity(Map.of("message", "User deleted successfully!"), HttpStatus.OK);
    }

    //GET - get All User
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //GET - get Single User
    @GetMapping("/{uid}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long uid) {
        return ResponseEntity.ok(userService.getUserById(uid));
    }
}
