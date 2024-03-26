package com.rebelo.springsecurityjwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.HEALTH_CHECK_PATH;

@RestController
@RequestMapping(HEALTH_CHECK_PATH)
@Tag(name = "Health Check API")
public class HealthCheckController {

    @Operation(summary = "GET Health Check")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHealthCheck() {
        return new ResponseEntity<>("Spring Security JWT application is up and running", HttpStatus.OK);
    }
}
