package com.loginapp.repository;

import com.loginapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY — Database Access Layer
 *
 * This interface is all you need to interact with the database.
 * By extending JpaRepository<User, Long> you get these methods for FREE:
 *
 *   save(user)         →  INSERT or UPDATE a user
 *   findById(id)       →  SELECT * FROM users WHERE id = ?
 *   findAll()          →  SELECT * FROM users
 *   deleteById(id)     →  DELETE FROM users WHERE id = ?
 *   existsById(id)     →  SELECT COUNT(*) FROM users WHERE id = ?
 *   count()            →  SELECT COUNT(*) FROM users
 *   ... and more
 *
 * The two type parameters are:
 *   User  → the entity this repository manages
 *   Long  → the type of the primary key (our id field)
 *
 * You can also declare custom queries just by naming the method correctly.
 * Spring Data JPA reads the method name and generates the SQL automatically.
 * No SQL needed!
 */
@Repository  // Marks this as a data-access component (also enables exception translation)
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * CUSTOM QUERY via method naming convention.
     *
     * Spring Data reads "findByEmail" and generates:
     *   SELECT * FROM users WHERE username = ?
     *
     * Returns Optional<User> instead of User because the user might not exist.
     * Optional forces you to handle the "not found" case explicitly —
     * much safer than returning null and risking a NullPointerException.
     *
     * Usage:
     *   Optional<User> user = userRepository.findByEmail("john");
     *   user.isPresent()  →  true if found
     *   user.get()        →  the actual User object (throws if empty, so check first)
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if an email is already taken.
     *
     * Spring generates: SELECT COUNT(*) FROM users WHERE email = ? > 0
     *
     * Used during registration to prevent duplicate usernames.
     */
    boolean existsByEmail(String email);
}
