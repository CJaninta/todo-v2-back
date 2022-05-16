package com.macky.todo.v2.controllers;

import com.macky.todo.v2.models.Todo;
import com.macky.todo.v2.models.TodoStep;
import com.macky.todo.v2.models.User;
import com.macky.todo.v2.services.TodoService;
import com.macky.todo.v2.services.UserService;
import com.macky.todo.v2.utils.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Slf4j
public class TodoAPI {

    private final UserService userService;
    private final TodoService todoService;

    @GetMapping("/user/{userId}/todo")
    public ResponseEntity<?> getTodos(@PathVariable Long userId, HttpServletRequest request) {

        String emailReq = (String) request.getAttribute("email");
        Optional<User> userData = userService.getUser(userId);

        if (!Validate.isValidAccessData(userData, emailReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't access data in this user.");
        }

        List<Todo> todos = todoService.getTodos(userId);

        return ResponseEntity.ok().body(todos);
    }

    @PostMapping("/user/{userId}/todo")
    public ResponseEntity<?> createTodo(@PathVariable Long userId, @RequestBody Todo todoReq, HttpServletRequest request) {

        String emailReq = (String) request.getAttribute("email");
        Optional<User> userData = userService.getUser(userId);

        if (!Validate.isValidAccessData(userData, emailReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't access data in this user.");
        }

        Todo todo = userData.map(user -> {
            todoReq.setUser(user);
            return todoService.saveTodo(todoReq);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{id}").buildAndExpand(userId).toUriString());

        return ResponseEntity.created(uri).body(todo);
    }

    @PutMapping("/user/{userId}/todo/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long userId, @PathVariable Long todoId, @RequestBody Todo todoReq, HttpServletRequest request) {

        String emailReq = (String) request.getAttribute("email");
        Optional<User> userData = userService.getUser(userId);

        if (!Validate.isValidAccessData(userData, emailReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't access data in this user.");
        }

        TodoStep step = todoReq.getStep();
        Optional<Todo> todo = todoService.updateTodo(todoId, todoReq);

        return ResponseEntity.ok().body(todo);
    }

    @DeleteMapping("/user/{userId}/todo/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long userId, @PathVariable Long todoId, HttpServletRequest request) {

        String emailReq = (String) request.getAttribute("email");
        Optional<User> userData = userService.getUser(userId);

        if (!Validate.isValidAccessData(userData, emailReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't access data in this user.");
        }

        Optional<Todo> todo = todoService.getTodo(todoId);

        if (todo.isEmpty()) {
            throw new ResourceNotFoundException("Todo is not found.");
        }

        todoService.deleteTodo(todoId);

        return ResponseEntity.ok().body("Delete todo success.");
    }

}
