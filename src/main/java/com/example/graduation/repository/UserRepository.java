package com.example.graduation.repository;

import com.example.graduation.model.User;

import java.util.Optional;

public interface UserRepository extends JpaReposiotry<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existByUsername(String username);
}
