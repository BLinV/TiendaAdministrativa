package com.store.www.controller;

import org.springframework.web.bind.annotation.RestController;

import com.store.www.dto.LoginRequest;
import com.store.www.dto.LoginResponse;
import com.store.www.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.autenticar(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
