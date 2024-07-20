package com.nhnacademy.client.exception;

public class ClientAuthenticationFailedException extends RuntimeException {
    public ClientAuthenticationFailedException(String message) {
        super(message);
    }
}
