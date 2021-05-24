package com.example.todo.demo.entity;

import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(length = 100)
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String todo;

    @Builder
    private Todo(String todo) {
        this.todo = todo;
    }

    public static Todo createFrom(String str) {
        return new Todo(str);
    }

    public TodoDto toDto() {
        return TodoDto.builder()
                .todo(todo)
                .seq(seq)
                .build();
    }
}
