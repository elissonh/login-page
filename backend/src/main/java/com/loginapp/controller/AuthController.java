package com.loginapp.controller;

import com.loginapp.dto.AuthDTOs.*;
import com.loginapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 *   Receive and parse the HTTP request
 *   Call the appropriate Service method
 *   Wrap the result in an HTTP response and return it
 *
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    // Spring injects AuthService automatically via constructor injection
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ─────────────────────────────────────────────────
    //  POST /api/auth/register
    // ─────────────────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─────────────────────────────────────────────────
    //  POST /api/auth/login
    // ─────────────────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────
    //  GET /api/auth/health
    // ─────────────────────────────────────────────────
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Backend is running!");
    }
}
