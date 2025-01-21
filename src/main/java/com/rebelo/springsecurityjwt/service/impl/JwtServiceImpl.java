package com.rebelo.springsecurityjwt.service.impl;

import com.rebelo.springsecurityjwt.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret.key}")
    private String secretKey;

    @Value("${security.jwt.expiration.time}")
    private long expirationTime;

    @Override
    public String extractUsername(String token) {
        log.info("Extracting username");
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("Validating token");
        final String username = extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public String generateToken(String username) {
        log.info("Generating token");
        return createToken(new HashMap<>(), username);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder().issuer("spring-security-filter").claims(extraClaims).subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(getSignKey()).compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
