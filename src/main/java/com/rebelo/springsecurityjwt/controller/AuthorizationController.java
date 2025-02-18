package com.rebelo.springsecurityjwt.controller;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.AUTHENTICATE_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.AUTHORIZATION_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.SIGN_UP_PATH;
import static com.rebelo.springsecurityjwt.util.UriUtil.buildSignUpUri;

import com.rebelo.springsecurityjwt.domain.request.AuthenticationRequest;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.response.AuthenticationResponse;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHORIZATION_PATH)
@Tag(name = "Authorization API")
public class AuthorizationController {

    private final AuthenticationService service;

    @Operation(summary = "POST Sign Up")
    @PostMapping(value = SIGN_UP_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        log.info("POST {}", AUTHORIZATION_PATH + SIGN_UP_PATH);
        UserResponse response = service.signUp(userCreateRequest);

        return ResponseEntity.created(buildSignUpUri(response.getId())).body(response);
    }

    @Operation(summary = "POST Authenticate")
    @PostMapping(value = AUTHENTICATE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        log.info("POST {}", AUTHORIZATION_PATH + AUTHENTICATE_PATH);
        return ResponseEntity.ok(service.authenticate(authenticationRequest));
    }
}
