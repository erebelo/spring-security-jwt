package com.rebelo.springsecurityjwt.controller;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.HEALTH_CHECK_PATH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(HEALTH_CHECK_PATH)
@Tag(name = "Health Check API")
public class HealthCheckController {

    @Operation(summary = "GET Health Check")
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getHealthCheck() {
        log.info("GET {}", HEALTH_CHECK_PATH);
        return ResponseEntity.ok("Spring Security JWT application is up and running");
    }
}
