package com.practice.javagroupiiminiproject.exception;

public class NotFoundException extends  RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}