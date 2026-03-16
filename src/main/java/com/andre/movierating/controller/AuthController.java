package com.andre.movierating.controller;

import com.andre.movierating.domain.dto.request.LoginRequestDTO;
import com.andre.movierating.domain.dto.request.RegisterRequestDTO;
import com.andre.movierating.domain.dto.response.AuthResponseDTO;
import com.andre.movierating.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request) {

        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}