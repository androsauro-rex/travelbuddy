package com.travelbuddy.exception;

@SuppressWarnings("serial")
public class DuplicatedResourceException extends RuntimeException {

    public DuplicatedResourceException(String message) {
        super(message);
    }

}
