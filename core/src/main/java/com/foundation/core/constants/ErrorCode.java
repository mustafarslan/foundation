package com.foundation.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_INPUT("TEST0001", HttpStatus.NOT_FOUND);

    private final String code;
    private final HttpStatus httpStatus;
}
