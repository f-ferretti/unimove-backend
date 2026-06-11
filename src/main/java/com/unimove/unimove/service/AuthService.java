package com.unimove.unimove.service;

import com.unimove.unimove.dto.request.LoginRequest;
import com.unimove.unimove.dto.response.LoginResponse;
import com.unimove.unimove.model.Role;
import com.unimove.unimove.model.User;
import com.unimove.unimove.repository.UserRepository;
import com.unimove.unimove.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> existing = userRepository.findByUsername(request.getUsername());

        if (existing.isEmpty()) {
            throw new RuntimeException("Utente non trovato");
        }

        User user = existing.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Credenziali non valide");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new LoginResponse(token, user.getUsername(), user.getRole().name(), user.getFullName());
    }

    public LoginResponse register(LoginRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username già esistente");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getUsername() + "@studenti.unimol.it")
                .fullName(request.getFullName() != null ? request.getFullName() : request.getUsername())
                .role(Role.STUDENT)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new LoginResponse(token, user.getUsername(), user.getRole().name(), user.getFullName());
    }
}