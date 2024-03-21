package com.rebelo.springsecurityjwt.service.impl;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.request.AuthenticationRequest;
import com.rebelo.springsecurityjwt.domain.request.RegisterRequest;
import com.rebelo.springsecurityjwt.domain.response.AuthenticationResponse;
import com.rebelo.springsecurityjwt.service.AuthenticationService;
import com.rebelo.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserEntity signUp(RegisterRequest registerRrequest) {
        UserEntity user = new UserEntity();
        user.setName(registerRrequest.getName());
        user.setEmail(registerRrequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRrequest.getPassword()));

        return userService.create(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        UserEntity user = userService.findByEmail(authRequest.getEmail());
        return new AuthenticationResponse(jwtService.generateToken(user.getEmail()));
    }
}
