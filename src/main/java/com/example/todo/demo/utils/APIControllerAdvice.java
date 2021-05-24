package com.example.todo.demo.utils;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.example.todo.demo.utils.ApiUtils.error;

@Slf4j
@RestControllerAdvice
class APIControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiUtils.ApiResult<?> handleValidationExceptions(MethodArgumentNotValidException ex){
        log.info("MethodArgumentNotValidException");
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return error(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiUtils.ApiResult<?> notFoundException(NotFoundException ex){
        log.info("NotFoundException");
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return error(errors, HttpStatus.BAD_REQUEST);
    }
}