package com.sast.goodnight2022backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author NuoTian
 * @date 2022/8/9
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {
    COMMON_ERROR(1000, "错误"),
    PARAM_ERROR(1001, "参数格式错误"),
    BLESSING_NOT_EXIST(2000, "您请求的祝福不存在"),
    NO_BLESSING(2001, "当前还没有任何祝福，赶快去发一个吧"),
    SERVER_ERROR(5000, "服务器内部错误");

    private final Integer errCode;
    private final String errMsg;
}
