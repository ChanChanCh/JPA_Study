package com.example.jpa.notice.execption;

public class NoticeNotFundException extends RuntimeException {
    public NoticeNotFundException(String message) {
        super(message);
    }
}
