package com.rebelo.springsecurityjwt.controller;

import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.List;

import static com.rebelo.springsecurityjwt.constants.BusinessConstants.FILTER_PATH;
import static com.rebelo.springsecurityjwt.constants.BusinessConstants.USER_PATH;

@RestController
@RequestMapping(USER_PATH)
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = FILTER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> findByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(service.update(id, userRequest));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
