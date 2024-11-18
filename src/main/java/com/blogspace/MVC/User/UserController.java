package com.blogspace.MVC.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * URL: localhost:8080/api/user/
     * Purpose: Fetches all the users in the user table
     * @return List of Users
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/user/:id
     * Purpose: Fetches a specific user in the user table
     * @param id - User's id to be fetched
     * @return User entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/user/
     * Purpose: Create a User entity
     * @param user - Request body is a User entity
     * @return Created User entity
     */
    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.createUser(user));
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
        userService.deleteUser(id);
        return ResponseEntity.ok().body("Deleted user successfully");
    }
}
