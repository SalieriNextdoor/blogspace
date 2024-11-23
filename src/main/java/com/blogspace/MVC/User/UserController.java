package com.blogspace.MVC.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

/**
 * Controller class is where all the requests are handled and responses are sent
 */
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecretKey key;

    public UserController(
            UserService userService,
            BCryptPasswordEncoder passwordEncoder, SecretKey jwtKey) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.key = jwtKey;
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/user/auth/
     * Purpose: Loads current user according to token
     * @return User entity
     */
    @GetMapping("/auth/")
    public ResponseEntity<User> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            User user = userService.getUserByEmail(userEmail);
            user.setPassword(null);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/user/:id
     * Purpose: Fetches a specific user in the user table
     * @param id - User's id to be fetched
     * @return User entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            user.setPassword(null);
            return ResponseEntity.ok().body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a POST request is made
     * URL: localhost:8080/api/user/
     * Purpose: Fetches a specific user in the user table and checks equality
     * @param user - Request body user entity
     * @return User entity
     */
    @PostMapping("/")
    public ResponseEntity<Object> getUser(@RequestBody User user) {
        try {
            User readUser = userService.getUserByEmail(user.getEmail());
            if (readUser != null && passwordEncoder.matches(user.getPassword(), readUser.getPassword())) {
                String token = generateToken(user);

                Map<String, String> response = new HashMap<>();
                response.put("token", token);

                return ResponseEntity.ok(response);
            }
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * This method is called when a POST request is made
     * URL: localhost:8080/api/user/signup
     * Purpose: Create a User entity
     * @param user - Request body is a User entity
     * @return Created User entity
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> saveUser(@RequestBody User user) {
        if (user.hasNull())
            return ResponseEntity.badRequest().body("Error: Missing fields.");

        if (!isValidEmail(user.getEmail()))
            return ResponseEntity.badRequest().body("Error: Invalid email.");

        if (!isValidUsername(user.getUsername()))
            return ResponseEntity.badRequest().body("Error: Invalid username.");

        if (user.getPassword().length() < 8)
            return ResponseEntity.badRequest().body("Error: Password must be at least 8 characters.");

        if (user.getUsername().length() < 4)
            return ResponseEntity.badRequest().body("Error: Username must be at least 4 characters.");

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        try {
            User created = userService.createUser(user);
            String token = generateToken(created);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * This method is called when a DELETE request is made
     * URL: localhost:8080/api/user/:id
     * Purpose: Delete a User entity
     * @param id - User's id to be deleted
     * @return a String message indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("User deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    private boolean isValidUsername(String username) {
        return username.matches("^\\w+$");
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("user", true)
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 21600 * 1000)) // 6 hours
                .signWith(key)
                .compact();
    }
}
