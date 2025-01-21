package com.rebelo.springsecurityjwt.service.impl;

import com.rebelo.springsecurityjwt.domain.request.AuthenticationRequest;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.response.AuthenticationResponse;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.service.AuthenticationService;
import com.rebelo.springsecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse signUp(UserCreateRequest userCreateRequest) {
        log.info("Signing up user");
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        return userService.insert(userCreateRequest);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        log.info("Authenticating user");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(token);
    }
}
