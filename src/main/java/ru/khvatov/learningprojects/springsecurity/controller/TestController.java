package ru.khvatov.learningprojects.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static ru.khvatov.learningprojects.springsecurity.controller.ApiVersions.API_V1;

@RequestMapping(API_V1 + "/test")
@RestController
public class TestController {
    @GetMapping
    public ResponseEntity<Map<String, String>> sayHello() {
        return ResponseEntity.ok(Map.of("message", "Hello!"));
    }
}
