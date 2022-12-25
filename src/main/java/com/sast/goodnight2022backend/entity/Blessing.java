package com.sast.goodnight2022backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author NuoTian
 * @date 2022/12/23
 */
@Data
public class Blessing {
    /**
     * 祝福语ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String uid;
    /**
     * 用户昵称
     */
    @NotBlank(message = "您似乎还没有填写昵称呢！")
    private String username;
    /**
     * 用户手机号
     */
    @NotBlank(message = "您似乎还没有填写手机号呢！")
    private String phone;
    /**
     * 用户邮箱
     */
    @NotBlank(message = "您似乎还没有填写邮箱呢！")
    private String mail;
    /**
     * 祝福语内容
     */
    @NotBlank(message = "您似乎还没有填写祝福内容呢！")
    private String blessing;
    /**
     * IP所在地
     */
    private String location;
    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
