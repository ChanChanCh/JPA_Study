package com.example.jpa.user.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String s){
        super(s);
    }

}
