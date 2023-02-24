package com.example.jpa.notice.execption;

public class DuplicateNoticeException extends RuntimeException {
    public DuplicateNoticeException(String message) {
        super(message);
    }
}
