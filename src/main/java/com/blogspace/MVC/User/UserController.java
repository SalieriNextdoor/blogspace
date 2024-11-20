package com.blogspace.MVC.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Controller class is where all the requests are handled and responses are sent
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

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
     * URL: localhost:8080/api/user/auth
     * Purpose: Fetches a specific user in the user table and checks equality
     * @param user - Request body user entity
     * @return User entity
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> getUser(@RequestBody User user) {
        try {
            User readUser = userService.getUserByEmail(user.getEmail());
            if (new BCryptPasswordEncoder().matches(user.getPassword(), readUser.getPassword())) {
                readUser.setPassword(null);
                return ResponseEntity.ok().body(readUser);
            }
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a POST request is made
     * URL: localhost:8080/api/user/
     * Purpose: Create a User entity
     * @param user - Request body is a User entity
     * @return Created User entity
     */
    @PostMapping("/")
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

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);

        try {
            return ResponseEntity.ok().body(userService.createUser(user));
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
}
