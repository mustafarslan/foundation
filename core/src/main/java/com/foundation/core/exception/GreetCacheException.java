package com.foundation.core.exception;

import com.foundation.core.constants.ErrorCode;

public class GreetCacheException extends BaseException {
    public GreetCacheException(ErrorCode errorCode) {
        super(errorCode);
    }
}
