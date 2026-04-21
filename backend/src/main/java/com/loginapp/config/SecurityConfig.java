package com.loginapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SECURITY CONFIGURATION
 *
 * By adding spring-boot-starter-security to pom.xml, Spring Boot automatically
 * locks down ALL endpoints — every request requires a login form by default.
 * This is great for production, but blocks our development completely.
 *
 * This class overrides that behavior to:
 *   1. Allow all requests to /api/auth/** (our public login/register endpoints)
 *   2. Allow the H2 console (so we can inspect the DB in the browser during dev)
 *   3. Disable CSRF protection (not needed for stateless REST APIs)
 *   4. Register BCryptPasswordEncoder as a Spring bean (so we can inject it in AuthService)
 *
 * @Configuration  → this class provides Spring beans (@Bean methods)
 * @EnableWebSecurity → activates Spring Security with our custom config
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityFilterChain defines the HTTP security rules.
     *
     * Think of it as a chain of filters every HTTP request passes through.
     * We configure what's allowed and what isn't.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF — not needed for stateless REST APIs.
            // Without this, every POST/PUT/DELETE gets a 403 unless it sends
            // a CSRF token, which REST clients (Postman, React fetch) don't do.
            .csrf(AbstractHttpConfigurer::disable)

            // Register the CORS configuration at the Spring Security filter level.
            // @CrossOrigin on the controller alone is not enough — Security's filter
            // chain runs BEFORE the controller and will reject preflight OPTIONS
            // requests with 403 if CORS isn't configured here too.
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )

            // Allow H2 console to render inside an iframe (dev only)
            .headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );

        return http.build();
    }

    /**
     * CORS Configuration Source
     *
     * This tells Spring Security which cross-origin requests are allowed.
     * Without this, the browser's preflight OPTIONS request gets blocked
     * before it ever reaches your controller — resulting in a 403.
     *
     * For development we allow everything (*).
     * In production, replace allowedOrigins with your actual frontend URL,
     * e.g. "https://yourname.github.io"
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*"));                          // any origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));                          // any header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);                 // apply to all paths
        return source;
    }

    /**
     * BCryptPasswordEncoder BEAN
     *
     * @Bean tells Spring: "create one instance of this and keep it in the
     * application context. When something declares a BCryptPasswordEncoder
     * dependency, inject this instance."
     *
     * BCrypt is used to:
     *   encode("mypassword")    →  "$2a$10$..." (hash, done at registration)
     *   matches("mypassword", "$2a$10$...") →  true (verification, done at login)
     *
     * The "strength" (default 10) controls how many rounds of hashing are done.
     * Higher = more secure but slower. 10 is the industry standard balance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}