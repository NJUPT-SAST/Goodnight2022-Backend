package com.sast.goodnight2022backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author NuoTian
 * @date 2022/12/23
 */
@Data
@TableName("`like`")
public class Like {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 祝福语ID
     */
    private Integer bid;
    /**
     * 用户ID
     */
    private String uid;
}
