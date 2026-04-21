package com.loginapp;

import com.loginapp.dto.AuthDTOs.*;
import com.loginapp.exception.AuthExceptions.*;
import com.loginapp.model.User;
import com.loginapp.repository.UserRepository;
import com.loginapp.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private AuthRequest validRequest;

    private RegisterRequest validRegisterRequest;

    @BeforeEach
    void setUp() {
        validRequest = new AuthRequest("john@gmail.com", "secret123");
        validRegisterRequest = new RegisterRequest("john@gmail.com", "John", "secret123");
    }

    // ─────────────────────────
    //  REGISTER TESTS
    // ─────────────────────────

    @Test
    @DisplayName("Register: success with new username")
    void register_success() {
        // Arrange
        when(userRepository.existsByEmail("john@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("$hashed$");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        AuthResponse response = authService.register(validRegisterRequest);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("john@gmail.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("secret123");
    }

    @Test
    @DisplayName("Register: fails if email already exists")
    void register_duplicateEmail_throwsException() {
        // Arrange
        when(userRepository.existsByEmail("john@gmail.com")).thenReturn(true);

        // Assert: expect this exception
        assertThrows(UserAlreadyExistsException.class,
                () -> authService.register(validRegisterRequest));

        // save should never be called
        verify(userRepository, never()).save(any());
    }

    // ─────────────────────────
    //  LOGIN TESTS
    // ─────────────────────────

    @Test
    @DisplayName("Login: success with correct credentials")
    void login_success() {
        // Arrange
        User existingUser = new User(1L, "john@gmail.com", "John", "$hashed$");
        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("secret123", "$hashed$")).thenReturn(true);

        // Act
        AuthResponse response = authService.login(validRequest);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("john@gmail.com", response.getEmail());
    }

    @Test
    @DisplayName("Login: fails when email does not exist")
    void login_userNotFound_throwsException() {
        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(validRequest));
    }

    @Test
    @DisplayName("Login: fails when password is wrong")
    void login_wrongPassword_throwsException() {
        User existingUser = new User(1L, "john@gmail.com", "John", "$hashed$");
        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(validRequest));
    }
}
