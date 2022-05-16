package com.macky.todo.v2.services;

import com.macky.todo.v2.models.Todo;
import com.macky.todo.v2.repository.TodoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TodoServiceImpl implements TodoService{

    private final TodoRepo todoRepo;

    @Override
    public Todo saveTodo(Todo todo) {
        log.info("Saving todo {} to server", todo.getUser().getId());
        todo.setCreated_date(LocalDateTime.now());
        return todoRepo.save(todo);
    }

    @Override
    public Optional<Todo> getTodo(Long todoId) {
        log.info("Finding todo by id from server.");
        return todoRepo.findById(todoId);
    }

    @Override
    public List<Todo> getTodos(Long userId) {
        log.info("Getting all todo from server.");
        return todoRepo.findByUser_Id(userId);
    }

    @Override
    public Optional<Todo> updateTodo(Long todoId, Todo todoReq) {
        Optional<Todo> todo = todoRepo.findById(todoId);

        if (todo.isEmpty()) {
            throw new ResourceNotFoundException("Todo is not found.");
        }

        Optional<Todo> updatedTodo = Optional.ofNullable(todo.map(todoValue -> {
            todoValue.setTitle(todoReq.getTitle());
            todoValue.setDetail(todoReq.getDetail());
            todoValue.setStep(todoReq.getStep());
            todoValue.setUpdated_date(LocalDateTime.now());
            return todoRepo.save(todoValue);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found todo with id = " + todoId)));;

        return updatedTodo;
    }

    @Override
    public void deleteTodo(Long todoId) {
        todoRepo.deleteById(todoId);
    }

}
