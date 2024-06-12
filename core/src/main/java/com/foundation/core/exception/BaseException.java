package com.foundation.core.exception;

import com.foundation.core.constants.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException{
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }
}
