package com.example.javaassignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GoalsNotFoundException extends RuntimeException{
    public GoalsNotFoundException(String exception){
        super(exception);
    }
}