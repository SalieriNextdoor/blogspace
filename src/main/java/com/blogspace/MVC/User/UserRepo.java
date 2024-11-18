package com.blogspace.MVC.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository is an interface that provides access to data in a database
 */
public interface UserRepo extends JpaRepository<User, Integer> {
}
