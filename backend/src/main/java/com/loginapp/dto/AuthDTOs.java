package com.loginapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTOs — Data Transfer Objects
 *
 * WHY DTOs instead of using the User model directly?
 *   The User model is tied to the database. DTOs are "shapes" of data
 *   that travel across the HTTP boundary — you control exactly what
 *   comes in and goes out, independent of the DB structure.
 *
 *   HTTP Request JSON  →  DTO  →  Service  →  Model  →  Database
 *   Database  →  Model  →  Service  →  Response DTO  →  HTTP Response JSON
 *
 * NOTE: Lombok was removed. Getters/setters/constructors are written
 * explicitly so the code compiles without annotation processing setup.
 */
public class AuthDTOs {

    // ─────────────────────────────────────────────
    // REQUEST DTO  (data coming IN from the client)
    // ─────────────────────────────────────────────
    public static class AuthRequest {

        @NotBlank(message = "Email is required")
        @Size(min = 3, max = 50, message = "Email must be a valid email")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 3, max = 100, message = "Password must be at least 3 characters")
        private String password;

        // Default constructor — required by Jackson to deserialize JSON into this object
        public AuthRequest() {}

        public AuthRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // ─────────────────────────────────────────────
    // REQUEST DTO  (data coming IN from the client)
    // ─────────────────────────────────────────────
    public static class RegisterRequest {

        @NotBlank(message = "Email is required")
        @Size(min = 3, max = 50, message = "Email must be a valid email")
        private String email;

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 100, message = "Username must be at least 3 characters")
        private String username;

        @NotBlank(message = "Password is required")
        @Size(min = 3, max = 100, message = "Password must be at least 3 characters")
        private String password;

        // Default constructor — required by Jackson to deserialize JSON into this object
        public RegisterRequest() {}

        public RegisterRequest(String email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // ──────────────────────────────────────────────
    // SUCCESS RESPONSE DTO  (data going OUT to the client)
    // ──────────────────────────────────────────────

    // Returned on success. Send username and email to frontend
    public static class AuthResponse {
        private boolean success;
        private String message;
        private String username;
        private String email;

        public AuthResponse(boolean success, String message, String email, String username) {
            this.success = success;
            this.message = message;
            this.username = username;
            this.email = email;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }


    // ──────────────────────────────────────────────
    // ERROR RESPONSE DTO
    // ──────────────────────────────────────────────

    // Returned on error (wrong password, duplicate email, etc.)
    // A dedicated error DTO means the frontend always knows what fields to expect.
    public static class ErrorResponse {
        private boolean success = false;
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
