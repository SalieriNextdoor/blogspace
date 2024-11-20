package com.blogspace.MVC.User;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(int id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent())
            return user.get();
        log.error("User with id: {} does not exist.", id);
        return null;
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
