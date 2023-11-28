package com.example.nalsam.user.exception;

public class PasswordNotCorrectException extends RuntimeException{

    private static final String message = "비밀번호가 불일치 합니다.";

    public PasswordNotCorrectException() {
        super(message);
    }
}
