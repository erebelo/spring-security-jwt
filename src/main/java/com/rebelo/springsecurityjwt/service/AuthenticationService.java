package com.rebelo.springsecurityjwt.service;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.request.AuthenticationRequest;
import com.rebelo.springsecurityjwt.domain.request.RegisterRequest;
import com.rebelo.springsecurityjwt.domain.response.AuthenticationResponse;

public interface AuthenticationService {

    UserEntity signUp(RegisterRequest registerRrequest);

    AuthenticationResponse authenticate(AuthenticationRequest authRequest);

}
