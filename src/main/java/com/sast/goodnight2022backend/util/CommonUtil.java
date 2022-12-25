package com.sast.goodnight2022backend.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author NuoTian
 * @date 2022/12/24
 */
public class CommonUtil {
    /**
     * 获得10个字符长度的字符串
     * @param str 字符串
     * @return 10个长度字符串
     */
    @NotNull
    @Contract(pure = true)
    public static String cutString(@Nullable String str) {
        if (str == null || str.isBlank())
            return "";
        if (str.length() <= 15)
            return str;
        return str.substring(0, 15);
    }
}
