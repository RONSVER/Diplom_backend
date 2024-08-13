package com.superstore.exceptions;

public class NoUniqueUserEmailException extends RuntimeException{

    public NoUniqueUserEmailException(String message) {

        super(message);
    }
}
