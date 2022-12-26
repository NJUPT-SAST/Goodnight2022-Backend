package com.sast.goodnight2022backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author NuoTian
 * @date 2022/12/26
 */
@Getter
@AllArgsConstructor
public enum BlessingStatusEnum {
    COMMON(1), // 正常祝愿
    BAN_ROLL(0); // 不允许被随机到

    private final int status;
}
