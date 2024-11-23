package com.blogspace.MVC.User;

import com.blogspace.util.exception.BadArgumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new BadArgumentException("User with id " + id + " does not exist."));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new BadArgumentException("User with email " + email + " does not exist."));
    }

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());

        try {
            User createdUser = userRepo.save(user);

            log.info("User with id: {} created successfully.", createdUser.getId());
            return createdUser;
        } catch(DataIntegrityViolationException e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new BadArgumentException("Username or email address already exists.");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while creating the user.");
        }
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            log.error("User with id: {} does not exist.", id);
            throw new BadArgumentException("User does not exist.");
        }
        userRepo.deleteById(id);
        log.info("User with id: {} deleted successfully.", id);
    }


}
