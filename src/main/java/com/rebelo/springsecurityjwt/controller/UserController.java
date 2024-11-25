package com.rebelo.springsecurityjwt.controller;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.FILTER_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.USERS_PATH;

import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS_PATH)
@Tag(name = "Users API")
public class UserController {

    private final UserService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "GET Users")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> findAll() {
        LOGGER.info("GET {}", USERS_PATH);
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "GET User by Id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        LOGGER.info("GET {}/{}", USERS_PATH, id);
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "GET User by Email")
    @GetMapping(value = FILTER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> findByEmail(@RequestParam("email") String email) {
        LOGGER.info("GET {}/{}", USERS_PATH + FILTER_PATH, email);
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @Operation(summary = "PUT Users")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        LOGGER.info("PUT {}/{}", USERS_PATH, id);
        return ResponseEntity.ok(service.update(id, userRequest));
    }

    @Operation(summary = "DELETE Users")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("DELETE {}/{}", USERS_PATH, id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
