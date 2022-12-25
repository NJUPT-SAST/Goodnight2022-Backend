package com.sast.goodnight2022backend.handler;

import com.sast.goodnight2022backend.enums.ErrorEnum;
import com.sast.goodnight2022backend.response.GlobalResponse;
import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author NuoTian
 * @date 2022/12/24
 */
@RestControllerAdvice
public class ValidExceptionHandler {
    /**
     * 处理请求参数格式错误 @RequestBody上使用@Valid 实体上使用@NotNull等，验证失败后抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> GlobalResponse<T> handlerValidationException(@NotNull MethodArgumentNotValidException e) {
        String messages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        return GlobalResponse.failure(messages);
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public <T> GlobalResponse<T> BindExceptionHandler(@NotNull BindException e) {
        String messages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        return GlobalResponse.failure(messages);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public <T> GlobalResponse<T> ConstraintViolationExceptionHandler(@NotNull ConstraintViolationException e) {
        return GlobalResponse.failure(e.getMessage());
    }

    /**
     * 参数格式异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> GlobalResponse<T> HttpMessageNotReadableExceptionHandler() {
        return GlobalResponse.failure(ErrorEnum.PARAM_ERROR);
    }
}
