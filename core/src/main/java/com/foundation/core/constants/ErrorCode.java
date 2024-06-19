package com.foundation.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_INPUT("TEST0001", HttpStatus.NOT_FOUND),
    INVALID_UPDATE("TEST0002", HttpStatus.NOT_FOUND),
    NOT_FOUND("TEST0003", HttpStatus.NOT_FOUND),
    COULD_NOT_REMOVE("TEST0004", HttpStatus.NOT_FOUND);

    private final String code;
    private final HttpStatus httpStatus;
}
