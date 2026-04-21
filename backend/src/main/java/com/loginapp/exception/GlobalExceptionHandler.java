package com.loginapp.exception;

import com.loginapp.dto.AuthDTOs.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * GLOBAL EXCEPTION HANDLER
 *
 * @RestControllerAdvice makes this class intercept exceptions thrown anywhere
 * in your controllers and convert them into proper HTTP responses.
 *
 * Without this, if your service throws an exception, Spring would return
 * an ugly default error page or a 500 Internal Server Error with a stack trace.
 *
 * With this handler:
 *   UserAlreadyExistsException   →  HTTP 409 + { "success": false, "message": "..." }
 *   InvalidCredentialsException  →  HTTP 401 + { "success": false, "message": "..." }
 *   Validation error             →  HTTP 400 + { "success": false, "message": "..." }
 *   Anything else                →  HTTP 500 + generic message (no stack trace exposed)
 *
 * The frontend always receives a consistent JSON shape, regardless of the error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles duplicate email during registration.
     * HTTP 409 Conflict is the correct status when a resource already exists.
     */
    @ExceptionHandler(AuthExceptions.UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
            AuthExceptions.UserAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)                  // 409
                .body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Handles wrong email or password during login.
     * HTTP 401 Unauthorized is the correct status for authentication failures.
     */
    @ExceptionHandler(AuthExceptions.InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            AuthExceptions.InvalidCredentialsException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)              // 401
                .body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Handles @Valid annotation failures (e.g., blank email, short password).
     *
     * When a request body fails validation, Spring throws MethodArgumentNotValidException.
     * We extract all field errors and join them into a single readable message.
     *
     * Example: "email: Email is required; password: Password must be at least 6 characters"
     *
     * HTTP 400 Bad Request — the client sent invalid data.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)               // 400
                .body(new ErrorResponse(errors));
    }

    /**
     * Safety net — catches anything not handled above.
     * Returns a generic message WITHOUT leaking internal details (stack traces, etc.)
     * to the client. Always log the real exception on the server side.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // In production you'd use a logger: log.error("Unexpected error", ex);
        System.err.println("Unexpected error: " + ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)     // 500
                .body(new ErrorResponse("An unexpected error occurred. Please try again."));
    }
}
