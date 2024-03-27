package com.rebelo.springsecurityjwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String generateToken(String username);

}
