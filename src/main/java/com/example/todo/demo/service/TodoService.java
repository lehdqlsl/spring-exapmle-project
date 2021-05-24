package com.example.todo.demo.service;

import com.example.todo.demo.entity.Todo;
import com.example.todo.demo.entity.TodoDto;
import com.example.todo.demo.repository.TodoRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoDto saveTodo(Todo todo) {
        return todoRepository.save(todo).toDto();
    }

    public List<TodoDto> getAllTodo() {
        List<TodoDto> ret = new ArrayList<>();
        todoRepository.findAll().forEach(x->ret.add(x.toDto()));
        return ret;
    }

    public void findAndDelete(int seq) throws NotFoundException {
        Todo todo = todoRepository.findById(seq).orElseThrow(() -> new NotFoundException("해당 Todo 항목이 없습니다."));
        deleteById(todo.getSeq());
    }

    public void deleteAllTodo() {
        todoRepository.deleteAll();
    }

    public void deleteById(int seq) {
        todoRepository.deleteById(seq);
    }

    public TodoDto findAndModify(int seq, Todo toTodo) throws NotFoundException {
        Todo todo = todoRepository.findById(seq).orElseThrow(() -> new NotFoundException("해당 Todo 항목이 없습니다."));
        todo.setTodo(toTodo.getTodo());
        return todoRepository.save(todo).toDto();
    }
}
