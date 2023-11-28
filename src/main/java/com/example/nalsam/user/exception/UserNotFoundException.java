package com.example.nalsam.user.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String message = "해당 회원 정보가 없습니다.";

    public UserNotFoundException() {
        super(message);
    }
}
