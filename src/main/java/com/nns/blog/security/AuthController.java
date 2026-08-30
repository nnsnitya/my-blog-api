package com.nns.blog.security;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.common.UserDto;
import com.nns.blog.dto.requests.JwtAuthRequest;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.JwtAuthResponse;
import com.nns.blog.dto.responses.ResponseHandler;
import com.nns.blog.dto.responses.UserDtoRp;
import com.nns.blog.entities.User;
import com.nns.blog.exceptions.ApiException;
import com.nns.blog.mappers.UserMapper;
import com.nns.blog.mappers.UserRpMapper;
import com.nns.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.VERSION1+"/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRpMapper userRpMapper;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtAuthRequest loginReq) throws Exception {
        this.authenticate(loginReq.username(), loginReq.password());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginReq.username());
        String token = this.jwtUtils.generateToken(userDetails);
        UserDtoRp userDtoRp = userRpMapper.toDto((User) userDetails);
        JwtAuthResponse response = new JwtAuthResponse(token, userDtoRp);

        return ResponseHandler.generateResp(response, "Token Generated", HttpStatus.OK, Code.SUCCESS.getCode());

    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid Username or password !!");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        UserDtoRp registeredUser = this.userService.registerUser(userDto);
        return ResponseHandler.generateResp(registeredUser, "User registered successfully", HttpStatus.OK, Code.SUCCESS.getCode());
    }

}
