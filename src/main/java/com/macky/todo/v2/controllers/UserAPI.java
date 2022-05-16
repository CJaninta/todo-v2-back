package com.macky.todo.v2.controllers;

import com.macky.todo.v2.models.User;
import com.macky.todo.v2.services.UserService;
import com.macky.todo.v2.utils.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Slf4j
public class UserAPI {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) {

        log.info("User Register");

        if (!Validate.isValidEmailAddress(user.getEmail())) {
            log.info("Email invalid format.");
            return ResponseEntity.badRequest().body("Email invalid format.");
        }

        if (!userService.hasEmail(user.getEmail())) {
            log.info("This email has been registered.");
            return ResponseEntity.badRequest().body("This email has been registered.");
        }

        User createdUser = userService.saveUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{id}").buildAndExpand(createdUser.getId()).toUriString()); // uri = /user/{id} สังเกตได้จากเวลาเรา post แล้วไปดูที่ header

        return ResponseEntity.created(uri).body(createdUser);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId, HttpServletRequest request) {

        String emailReq = (String) request.getAttribute("email");
        Optional<User> userData = userService.getUser(userId);

        if (!Validate.isValidAccessData(userData, emailReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't access data in this user.");
        }

        log.info("Get user id = {}",userId);
        return ResponseEntity.ok().body(userData);
    }

}
