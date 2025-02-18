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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(USERS_PATH)
@Tag(name = "Users API")
public class UserController {

    private final UserService service;

    @Operation(summary = "GET Users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> findAll() {
        log.info("GET {}", USERS_PATH);
        return service.findAll();
    }

    @Operation(summary = "GET User by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse findById(@PathVariable Long id) {
        log.info("GET {}/{}", USERS_PATH, id);
        return service.findById(id);
    }

    @Operation(summary = "GET User by Email")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = FILTER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse findByEmail(@RequestParam("email") String email) {
        log.info("GET {}/{}", USERS_PATH + FILTER_PATH, email);
        return service.findByEmail(email);
    }

    @Operation(summary = "PUT Users")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        log.info("PUT {}/{}", USERS_PATH, id);
        return service.update(id, userRequest);
    }

    @Operation(summary = "DELETE Users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        log.info("DELETE {}/{}", USERS_PATH, id);
        service.delete(id);
    }
}
