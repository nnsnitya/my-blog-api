package com.nns.blog.security;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.requests.JwtAuthRequest;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.JwtAuthResponse;
import com.nns.blog.dto.responses.ResponseHandler;
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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtAuthRequest loginReq) throws Exception {
        this.authenticate(loginReq.username(), loginReq.password());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginReq.username());
        String token = this.jwtUtils.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse(token);
        return ResponseHandler.generateResp("Token Generated", HttpStatus.OK, response, Code.SUCCESS.getCode());

    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new Exception("Invalid Details !!");
        }

    }

}
