package com.andre.movierating.service;

import com.andre.movierating.domain.dto.request.LoginRequestDTO;
import com.andre.movierating.domain.dto.request.RegisterRequestDTO;
import com.andre.movierating.domain.dto.response.AuthResponseDTO;
import com.andre.movierating.domain.model.User;
import com.andre.movierating.repository.UserRepository;
import com.andre.movierating.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsername());

        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }
}