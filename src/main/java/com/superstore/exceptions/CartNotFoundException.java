package com.superstore.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String msg) {
        super(msg);
    }
}
