package com.sast.goodnight2022backend.response;

import com.sast.goodnight2022backend.enums.ErrorEnum;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author NuoTian
 * @date 2022/12/24
 */
@Data
public class GlobalResponse<T> {
    private boolean success;
    private Integer errCode;
    private String errMsg;
    private T data;

    @NotNull
    @Contract(" -> new")
    public static GlobalResponse<String> success() {
        return success("OK");
    }

    @NotNull
    @Contract("_ -> new")
    public static <T> GlobalResponse<T> success(T data) {
        GlobalResponse<T> response = new GlobalResponse<>();
        response.setSuccess(true);
        response.setErrCode(null);
        response.setErrMsg(null);
        response.setData(data);
        return response;
    }

    @NotNull
    @Contract(" -> new")
    public static <T> GlobalResponse<T> failure() {
        return failure(ErrorEnum.COMMON_ERROR);
    }

    @NotNull
    @Contract("_ -> new")
    public static <T> GlobalResponse<T> failure(String msg) {
        GlobalResponse<T> response = new GlobalResponse<>();
        response.setSuccess(false);
        response.setErrCode(ErrorEnum.COMMON_ERROR.getErrCode());
        response.setErrMsg(msg);
        response.setData(null);
        return response;
    }

    @NotNull
    @Contract("_ -> new")
    public static <T> GlobalResponse<T> failure(@NotNull ErrorEnum errorEnum) {
        GlobalResponse<T> response = new GlobalResponse<>();
        response.setSuccess(false);
        response.setErrCode(errorEnum.getErrCode());
        response.setErrMsg(errorEnum.getErrMsg());
        response.setData(null);
        return response;
    }
}
