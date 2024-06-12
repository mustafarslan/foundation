package com.foundation.core.exception;

import com.foundation.core.constants.ErrorCode;

public class GreetingsException extends BaseException{

    public GreetingsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
