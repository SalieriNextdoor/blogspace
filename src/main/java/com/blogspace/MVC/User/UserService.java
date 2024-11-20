package com.blogspace.MVC.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public User getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist."));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " does not exist."));
    }

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());

        try {
            User createdUser = userRepo.save(user);

            log.info("User with id: {} created successfully.", createdUser.getId());
            return createdUser;
        } catch(DataIntegrityViolationException e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new IllegalArgumentException("Username or email address already exists.");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while creating the user.");
        }
    }

    public void deleteUser(int id) {
        if (!userRepo.existsById(id)) {
            log.error("User with id: {} does not exist.", id);
            throw new IllegalArgumentException("User does not exist.");
        }
        userRepo.deleteById(id);
        log.info("User with id: {} deleted successfully.", id);
    }


}
