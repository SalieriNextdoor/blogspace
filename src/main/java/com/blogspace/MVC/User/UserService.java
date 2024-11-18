package com.blogspace.MVC.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        log.info("User with id: {} doesn't exist", id);
        return null;
    }

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        User createdUser = userRepo.save(user);

        log.info("User with id: {} created successfully", createdUser.getId());
        return createdUser;
    }

    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }
}
