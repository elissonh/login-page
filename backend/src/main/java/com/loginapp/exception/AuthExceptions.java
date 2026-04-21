package com.loginapp.exception;

/**
 * CUSTOM EXCEPTIONS
 *
 * Why create custom exceptions instead of using generic ones like RuntimeException?
 *
 * 1. CLARITY: "UserAlreadyExistsException" is self-documenting.
 *    A developer reading the code immediately understands what went wrong.
 *
 * 2. CONTROL: You can catch specific exception types and return different
 *    HTTP status codes. UserAlreadyExistsException → 409 Conflict.
 *    InvalidCredentialsException → 401 Unauthorized. etc.
 *
 * 3. SEPARATION: Business logic errors (wrong password) are different from
 *    programming errors (NullPointerException). Custom exceptions make
 *    that boundary explicit.
 *
 * All exceptions here extend RuntimeException (unchecked), meaning you don't
 * need to declare "throws XyzException" on every method — they bubble up
 * automatically to the GlobalExceptionHandler.
 */
public class AuthExceptions {

    /**
     * Thrown during registration when the email is already taken.
     * The GlobalExceptionHandler will catch this and return HTTP 409 Conflict.
     */
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String email) {
            super("Email '" + email + "' is already taken");
        }
    }

    /**
     * Thrown during login when email doesn't exist or password is wrong.
     *
     * SECURITY NOTE: We use the same exception for both cases intentionally.
     * If we returned "user not found" vs "wrong password" separately,
     * an attacker could use that information to enumerate valid usernames.
     * Returning the same message for both keeps that information hidden.
     */
    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("Invalid email or password");
        }
    }
}
