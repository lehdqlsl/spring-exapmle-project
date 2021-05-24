package com.example.todo.demo.controller;

import com.example.todo.demo.entity.TodoDto;
import com.example.todo.demo.repository.TodoRepository;
import com.example.todo.demo.service.TodoService;
import com.example.todo.demo.utils.ApiUtils;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.todo.demo.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private static final String SUCCESS = "SUCCESS";
    final TodoService todoService;
    final TodoRepository todoRepository;

    @PostMapping("/todo")
    public ApiUtils.ApiResult<TodoDto> createTodo(@Valid @RequestBody TodoDto todo){
        return success(todoService.saveTodo(todo.toTodo()));
    }

    @PutMapping("/todo/{seq}")
    public ApiUtils.ApiResult<TodoDto> modifyTodo(@PathVariable int seq, @Valid @RequestBody TodoDto todo) throws NotFoundException { ;
        return success(todoService.findAndModify(seq,todo.toTodo()));
    }

    @GetMapping("/todo")
    public ApiUtils.ApiResult<List<TodoDto>> getTodo(){
        return success(todoService.getAllTodo());
    }

    @DeleteMapping("/todo/{seq}")
    public ApiUtils.ApiResult<String> deleteTodo(@PathVariable int seq) throws NotFoundException {
        todoService.findAndDelete(seq);
        return success(SUCCESS);
    }

    @DeleteMapping("/todo")
    public ApiUtils.ApiResult<String> deleteAllTodo(){
        todoService.deleteAllTodo();
        return success(SUCCESS);
    }
}
