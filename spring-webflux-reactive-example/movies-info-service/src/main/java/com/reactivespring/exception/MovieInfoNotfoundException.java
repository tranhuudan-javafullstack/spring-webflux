package com.reactivespring.exception;

public class MovieInfoNotfoundException extends RuntimeException {

    public MovieInfoNotfoundException(String message) {
        super(message);
    }
}
