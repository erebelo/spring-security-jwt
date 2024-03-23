package com.rebelo.springsecurityjwt.service;

import com.rebelo.springsecurityjwt.domain.request.AuthenticationRequest;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.response.AuthenticationResponse;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;

public interface AuthenticationService {

    UserResponse signUp(UserCreateRequest userCreateRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
