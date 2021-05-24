package com.example.todo.demo.repository;

import com.example.todo.demo.entity.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ToDoTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void todo_save_test() {
        Todo todo = Todo.createFrom("안녕하세요");
        Todo save = todoRepository.save(todo);
        assertThat(todo.getTodo()).isEqualTo(save.getTodo());
    }
}
