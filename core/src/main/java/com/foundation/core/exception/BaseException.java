package com.foundation.core.exception;

import com.foundation.core.constants.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;

    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    protected BaseException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode.getCode(), throwable);
        this.errorCode = errorCode;
    }
}
