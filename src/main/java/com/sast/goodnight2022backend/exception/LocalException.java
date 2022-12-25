package com.sast.goodnight2022backend.exception;

import com.sast.goodnight2022backend.enums.ErrorEnum;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author NuoTian
 * @date 2022/8/9
 */
@Getter
public class LocalException extends RuntimeException {
    private ErrorEnum errorEnum;

    public LocalException(String msg) {
        super(msg);
    }

    public LocalException(@NotNull ErrorEnum errorEnum) {
        super(errorEnum.getErrMsg());
        this.errorEnum = errorEnum;
    }

    public LocalException(String msg, Exception e) {
        super(msg, e);
    }

    public LocalException(@NotNull ErrorEnum errorEnum, Exception e) {
        super(errorEnum.getErrMsg(), e);
        this.errorEnum = errorEnum;
    }
}
