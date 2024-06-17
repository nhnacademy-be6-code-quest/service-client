package com.nhnacademy.auth.exception;

public class ClientEmailDuplicatesException extends RuntimeException {
    public ClientEmailDuplicatesException() {
        super("이미 존재하는 이메일입니다.");
    }
}
