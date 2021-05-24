package com.example.todo.demo.entity;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoDto {

    private int seq;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String todo;

    @Builder
    public TodoDto(@NotNull @NotBlank @Size(max = 50) String todo, int seq) {
        this.seq = seq;
        this.todo = todo;
    }

    public Todo toTodo() {
        return Todo.builder()
                .todo(todo)
                .build();
    }
}
