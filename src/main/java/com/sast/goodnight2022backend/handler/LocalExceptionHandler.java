package com.sast.goodnight2022backend.handler;

import com.sast.goodnight2022backend.exception.LocalException;
import com.sast.goodnight2022backend.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author NuoTian
 * @date 2022/12/24
 */
@Slf4j
@RestControllerAdvice
public class LocalExceptionHandler {
    @ExceptionHandler(value = LocalException.class)
    public <T> GlobalResponse<T> localException(LocalException e) {
        if (e == null) {
            return GlobalResponse.failure();
        } else if (e.getErrorEnum() != null) {
            return GlobalResponse.failure(e.getErrorEnum());
        } else {
            return GlobalResponse.failure(e.getMessage());
        }
    }
}
