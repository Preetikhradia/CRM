package com.Preeti.crm.service;

import com.Preeti.crm.dto.AuthResponse;
import com.Preeti.crm.dto.LoginRequest;
import com.Preeti.crm.dto.RegisterRequest;
import com.Preeti.crm.model.Role;
import com.Preeti.crm.model.User;
import com.Preeti.crm.repository.UserRepository;
import com.Preeti.crm.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException; // ✅ Import this

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        // ✅ FIX: Throw a 400 Bad Request instead of crashing
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default to SALES if no role is provided, or use the requested role
        if (request.getRole() == null || request.getRole().isEmpty()) {
            user.setRole(Role.SALES);
        } else {
            // Ensure the string matches the Enum (e.g. "ROLE_ADMIN")
            try {
                user.setRole(Role.valueOf(request.getRole()));
            } catch (IllegalArgumentException e) {
                user.setRole(Role.SALES); // Fallback
            }
        }

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }
}