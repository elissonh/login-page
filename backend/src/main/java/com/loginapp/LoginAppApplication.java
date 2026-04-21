package com.loginapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * APPLICATION ENTRY POINT
 *
 * @SpringBootApplication is a shortcut for three annotations:
 *   - @Configuration:        this class can define Spring "beans" (managed objects)
 *   - @EnableAutoConfiguration: Spring Boot auto-configures things based on
 *                            what's on the classpath (e.g., sees H2 → sets up a DB)
 *   - @ComponentScan:        scans this package and subpackages for classes
 *                            annotated with @Controller, @Service, @Repository, etc.
 *
 * When you run this class, Spring Boot:
 *   1. Starts an embedded Tomcat server
 *   2. Creates the H2 in-memory database
 *   3. Scans and registers all your components
 *   4. Maps your REST endpoints
 *   → Your API is live at http://localhost:8080
 */
@SpringBootApplication
public class LoginAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginAppApplication.class, args);
    }
}
