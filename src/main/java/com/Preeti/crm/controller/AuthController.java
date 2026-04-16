package com.Preeti.crm.controller;

import com.Preeti.crm.dto.AuthResponse;
import com.Preeti.crm.dto.LoginRequest;
import com.Preeti.crm.dto.RegisterRequest;
import com.Preeti.crm.model.Role;
import com.Preeti.crm.model.User;
import com.Preeti.crm.repository.UserRepository;
import com.Preeti.crm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
// ✅ Allow both frontend ports
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository; // Added for the promote hack

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // 🚨 EMERGENCY BACKDOOR: Call this to force someone to be an ADMIN
    // Usage: POST http://localhost:8080/api/auth/promote/doremii@gmail.com
    @PostMapping("/promote/{email}")
    public ResponseEntity<String> promoteToAdmin(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        user.setRole(Role.ADMIN);
        userRepository.save(user);

        return ResponseEntity.ok("SUCCESS: User " + email + " is now an ADMIN. Please logout and login again.");
    }
}