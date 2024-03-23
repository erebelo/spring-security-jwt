package com.rebelo.springsecurityjwt.service.impl;

import com.rebelo.springsecurityjwt.domain.request.AuthenticationRequest;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.response.AuthenticationResponse;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.service.AuthenticationService;
import com.rebelo.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse signUp(UserCreateRequest userCreateRequest) {
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        return userService.insert(userCreateRequest);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));

        var userResponse = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        return new AuthenticationResponse(jwtService.generateToken(userResponse.getUsername()));
    }
}
