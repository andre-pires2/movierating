package com.andre.movierating.controller;

import com.andre.movierating.domain.dto.request.LoginRequestDTO;
import com.andre.movierating.domain.dto.request.RegisterRequestDTO;
import com.andre.movierating.domain.dto.response.AuthResponseDTO;
import com.andre.movierating.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}