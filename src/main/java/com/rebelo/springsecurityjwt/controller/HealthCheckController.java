package com.rebelo.springsecurityjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rebelo.springsecurityjwt.constants.BusinessConstants.HEALTH_CHECK_PATH;

@RestController
@RequestMapping(HEALTH_CHECK_PATH)
public class HealthCheckController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHealthCheck() {
        return new ResponseEntity<>("Spring Security JWT application is up and running", HttpStatus.OK);
    }
}
