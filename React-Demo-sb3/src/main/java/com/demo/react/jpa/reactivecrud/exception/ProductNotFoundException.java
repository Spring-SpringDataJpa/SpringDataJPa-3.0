package com.demo.react.jpa.reactivecrud.exception;


import java.util.UUID;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(UUID message) {
        super(String.valueOf(message));
    }

}
