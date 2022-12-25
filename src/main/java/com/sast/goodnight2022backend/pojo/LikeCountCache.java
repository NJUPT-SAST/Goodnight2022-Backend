package com.sast.goodnight2022backend.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author NuoTian
 * @date 2022/12/25
 */
@Data
public class LikeCountCache {
    private Integer bid;
    private Integer count;
    private LocalDateTime date;
}
