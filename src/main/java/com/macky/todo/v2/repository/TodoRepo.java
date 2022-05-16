package com.macky.todo.v2.repository;

import com.macky.todo.v2.models.Todo;
import com.macky.todo.v2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepo extends JpaRepository<Todo, Long> {
    List<Todo> findByUser_Id(Long userId);
}
