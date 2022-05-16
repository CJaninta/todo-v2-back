package com.macky.todo.v2.services;

import com.macky.todo.v2.models.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    Todo saveTodo(Todo todo);
    Optional<Todo> getTodo(Long todoId);
    List<Todo> getTodos(Long userId);
    Optional<Todo> updateTodo(Long todoId, Todo todo);
    void deleteTodo(Long todoId);
}
