package com.foundation.core.handler;

import com.foundation.core.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBaseException(HttpServletRequest request, BaseException exception) {
        String message = messageSource.getMessage(exception.getErrorCode().getCode(), null, request.getLocale());
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(message);
    }
}
