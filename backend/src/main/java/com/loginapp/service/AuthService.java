package com.loginapp.service;

import com.loginapp.dto.AuthDTOs.*;
import com.loginapp.exception.AuthExceptions.*;
import com.loginapp.model.User;
import com.loginapp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * SERVICE — Business Logic Layer
 *
 * The Service is the brain of the application. It contains all the
 * business rules and coordinates between the Controller (HTTP layer)
 * and the Repository (database layer).
 *
 * RULE: Controllers should be thin. They receive requests, call a service
 * method, and return the result. All logic lives here, not in the controller.
 *
 * @Service marks this as a Spring-managed component. Spring creates one
 * instance of it and injects it wherever it's needed.
 *
 * NOTE: Lombok removed. Constructor is written explicitly for dependency injection.
 * Spring sees the single constructor and automatically injects the dependencies —
 * this is called Constructor Injection, the recommended approach in Spring.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Spring injects UserRepository and BCryptPasswordEncoder automatically
    // because they are registered as Spring beans (via @Repository and @Bean)
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ─────────────────────────────
    //  REGISTER
    // ─────────────────────────────

    /**
     * Creates a new user account.
     *
     * Flow:
     *   1. Check if email is already taken
     *   2. Hash the plain-text password with BCrypt
     *   3. Save the new User to the database
     *   4. Return a success response
     *
     * If email is taken → throws UserAlreadyExistsException
     *   → caught by GlobalExceptionHandler → returns HTTP 409 to client
     */
    public AuthResponse register(RegisterRequest request) {

        // Step 1: Check for duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        // Step 2: Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Step 3: Build and persist the User entity
        User newUser = new User(null, request.getEmail(), request.getUsername(), hashedPassword);
        userRepository.save(newUser); // JPA: INSERT INTO users

        // Step 4: Return success
        return new AuthResponse(true, "Account created successfully!", newUser.getEmail(), newUser.getUsername());
    }

    // ─────────────────────────────
    //  LOGIN
    // ─────────────────────────────

    /**
     * Validates login credentials.
     */
    public AuthResponse login(AuthRequest request) {

        // Find the user, or throw if not found.
        // Optional.orElseThrow() — if the Optional is empty, throws the given exception.
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // Compare plain-text password with the stored BCrypt hash.
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        // Step 3: Credentials are valid — return success
        return new AuthResponse(true, "Welcome!", user.getEmail(), user.getUsername());
    }
}
