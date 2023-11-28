package com.example.nalsam.user.exception;

public class UserAlreadyExistException extends RuntimeException{

    private static final String message = "이미 존재하는 회원 ID 입니다.";

    public UserAlreadyExistException() { super(message) ; }

}
