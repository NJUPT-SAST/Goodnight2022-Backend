package com.sast.goodnight2022backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sast.goodnight2022backend.entity.Blessing;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NuoTian
 * @date 2022/12/23
 */
@Mapper
public interface BlessingMapper extends BaseMapper<Blessing> {
    Blessing getRandBlessing();
}
