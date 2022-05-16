package com.macky.todo.v2.services;

import com.macky.todo.v2.models.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUser(Long userId);
    User getUserByEmail(String email);
    boolean hasEmail(String email);
}

