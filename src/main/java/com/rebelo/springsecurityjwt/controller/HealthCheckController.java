package com.rebelo.springsecurityjwt.controller;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.HEALTH_CHECK_PATH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HEALTH_CHECK_PATH)
@Tag(name = "Health Check API")
public class HealthCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @Operation(summary = "GET Health Check")
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getHealthCheck() {
        LOGGER.info("GET {}", HEALTH_CHECK_PATH);
        return ResponseEntity.ok("Spring Security JWT application is up and running");
    }
}
