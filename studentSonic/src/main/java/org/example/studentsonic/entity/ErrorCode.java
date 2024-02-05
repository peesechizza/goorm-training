package org.example.studentsonic.entity;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    OK(2000, "OK", HttpStatus.OK), BAD_REQUEST(5000, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    // INTERNAL_SERVER_ERROR
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
