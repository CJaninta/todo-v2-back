package com.macky.todo.v2.services;

import com.macky.todo.v2.models.User;
import com.macky.todo.v2.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving user {} {} to server.", user.getFirst_name(), user.getLast_name());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUser(Long userId) {
        log.info("Getting user id =  {} from server.", userId);

        Optional<User> result = userRepo.findById(userId);
        if (result.isEmpty()) {
            throw new RuntimeException("Can't find user.");
        }

        return result;
    }

    @Override
    public User getUserByEmail(String email) {
        User result = userRepo.findByEmail(email);
        if (result == null) {
            throw new RuntimeException("Can't find user by email.");
        }

        return result;
    }

    @Override
    public boolean hasEmail(String email) {
        User result = userRepo.findByEmail(email);
        return result == null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            log.error("User is not found.");
            throw new UsernameNotFoundException("User is not found.");
        }
        log.info("User is found.");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
