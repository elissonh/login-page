package com.loginapp.model;

import jakarta.persistence.*;

/**
 * MODEL — User Entity
 *
 * This class represents a row in the "users" database table.
 * JPA reads these annotations and handles all table creation and queries.
 *
 * LAYER ROLE: The Model is pure data — no business logic lives here.
 * It's just a Java representation of what gets stored in the database.
 *
 * NOTE: Lombok removed. All constructors/getters/setters are written explicitly.
 */
@Entity                    // Marks this class as a JPA entity (maps to a DB table)
@Table(name = "users")     // The actual table name in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String username;

    /**
     * Stored hashed (via BCrypt).
     * length = 255 because BCrypt hashes are ~60 chars — extra room to spare.
     */
    @Column(nullable = false, length = 255)
    private String password;

    // JPA requires a no-arg constructor to instantiate entities via reflection
    public User() {}

    public User(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
