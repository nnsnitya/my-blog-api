package com.nns.blog.controllers;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.common.UserDto;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.ResponseHandler;
import com.nns.blog.services.UserService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiConstants.VERSION1+"/users")
public class UserController {
    @Autowired
    private UserService userService;

    //POST - create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    //ADMIN
    //DELETE - delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long uid) {
        userService.deleteUser(uid);
        return new ResponseEntity(Map.of("message", "User deleted successfully!"), HttpStatus.OK);
    }

    //GET - get All User
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
            )),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseHandler.generateResp(userService.getAllUsers(), "All users", HttpStatus.OK, Code.SUCCESS.getCode());
    }

    //GET - get Single User
    @GetMapping("/{uid}")
    public ResponseEntity<Object> getSingleUser(@PathVariable Long uid) {
        return ResponseHandler.generateResp(userService.getUserById(uid), "Single User", HttpStatus.OK, Code.SUCCESS.getCode());
    }
}
