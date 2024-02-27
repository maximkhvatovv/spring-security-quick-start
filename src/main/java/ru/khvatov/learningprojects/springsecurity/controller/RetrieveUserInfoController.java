package ru.khvatov.learningprojects.springsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

import static ru.khvatov.learningprojects.springsecurity.controller.ApiVersions.API_V1;

@RequestMapping(API_V1 + "/greetings")
@RestController
public class RetrieveUserInfoController {
    @GetMapping("/v1")
    public ResponseEntity<Map<String, String>> sayHelloV1() {
        final Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final String username = ((UserDetails) principal).getUsername();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Hello, %s".formatted(username)));
    }

    @GetMapping("/v2")
    public ResponseEntity<Map<String, String>> sayHelloV2(HttpServletRequest request) {
        final Authentication authentication = (Authentication) request.getUserPrincipal();
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final String username = userDetails.getUsername();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Hello, %s".formatted(username)));
    }

    @GetMapping("/v3")
    public ResponseEntity<Map<String, String>> sayHelloV3(@AuthenticationPrincipal UserDetails userDetails) {
        final String username = userDetails.getUsername();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Hello, %s".formatted(username)));
    }

    @GetMapping("/v4")
    public ResponseEntity<Map<String, String>> sayHelloV4(Principal principal) {
        final Authentication authentication = (Authentication) principal;
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String username = userDetails.getUsername();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Hello, %s".formatted(username)));
    }
}
