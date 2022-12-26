package com.sast.goodnight2022backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sast.goodnight2022backend.entity.Blessing;
import com.sast.goodnight2022backend.entity.Like;
import com.sast.goodnight2022backend.enums.BlessingStatusEnum;
import com.sast.goodnight2022backend.enums.ErrorEnum;
import com.sast.goodnight2022backend.exception.LocalException;
import com.sast.goodnight2022backend.mapper.BlessingMapper;
import com.sast.goodnight2022backend.mapper.LikeMapper;
import com.sast.goodnight2022backend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author NuoTian
 * @date 2022/12/24
 */
@Slf4j
@Service
public class BlessingService {
    private LikeMapper likeMapper;
    private BlessingMapper blessingMapper;

    private RedisService redisService;

    @Autowired
    public void setLikeMapper(LikeMapper likeMapper) {
        this.likeMapper = likeMapper;
    }

    @Autowired
    public void setBlessingMapper(BlessingMapper blessingMapper) {
        this.blessingMapper = blessingMapper;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public HashMap<String, Object> getBlessing(String uid) {
        if (uid == null || uid.isBlank())
            throw new LocalException(ErrorEnum.PARAM_ERROR);

        log.info("{}：获取已发送的祝福", CommonUtil.cutString(uid));

        val blessing = blessingMapper.selectOne(new LambdaQueryWrapper<Blessing>()
                .eq(Blessing::getUid, uid));
        val response = new HashMap<String, Object>();
        if (blessing == null || blessing.getBlessing().isBlank()) {
            response.put("isSent", false);
            response.put("content", null);
            return response;
        }
        val responseBlessing = new HashMap<String, String>();
        responseBlessing.put("username", blessing.getUsername());
        responseBlessing.put("phone", blessing.getPhone());
        responseBlessing.put("mail", blessing.getMail());
        responseBlessing.put("blessing", blessing.getBlessing());
        response.put("isSent", true);
        response.put("content", responseBlessing);
        return response;
    }

    public void sendBlessing(@NotNull Blessing blessing) {
        log.info("{}：发送祝福", CommonUtil.cutString(blessing.getUid()));

        blessing.setDate(LocalDateTime.now());

        val blessingDB = blessingMapper.selectOne(new LambdaQueryWrapper<Blessing>()
                .eq(Blessing::getUid, blessing.getUid()));
        if (blessingDB == null) {
            blessing.setId(null);
            blessing.setStatus(null);
            blessingMapper.insert(blessing);
        } else {
            blessing.setId(blessingDB.getId());
            blessing.setStatus(blessingDB.getStatus());
            blessingMapper.updateById(blessing);
        }
    }

    public HashMap<String, Object> likeBlessing(Integer bid, String uid) {
        if (uid == null || uid.isBlank() || bid == null)
            throw new LocalException(ErrorEnum.PARAM_ERROR);

        log.info("{}：点赞祝福{}", CommonUtil.cutString(uid), bid);

        val count = blessingMapper.selectCount(new LambdaQueryWrapper<Blessing>()
                .eq(Blessing::getId, bid));
        if (count == 0)
            throw new LocalException(ErrorEnum.BLESSING_NOT_EXIST);

        val response = new HashMap<String, Object>();
        val likeDB = likeMapper.selectOne(new LambdaQueryWrapper<Like>()
                .eq(Like::getBid, bid)
                .eq(Like::getUid, uid));
        if (likeDB == null) {
            // 点赞
            val like = new Like();
            like.setBid(bid);
            like.setUid(uid);
            likeMapper.insert(like);

            redisService.cacheLikeCountsPlus(bid);

            response.put("isLiked", true);
        } else {
            // 取消点赞
            likeMapper.deleteById(likeDB);

            redisService.cacheLikeCountsMinus(bid);

            response.put("isLiked", false);
        }
        return response;
    }

    public HashMap<String, Object> getRandBlessing(String uid) {
        if (uid == null || uid.isBlank())
            throw new LocalException(ErrorEnum.PARAM_ERROR);

        log.info("{}：获取随机祝福", CommonUtil.cutString(uid));

        val randBlessing = blessingMapper.getRandBlessing();
        if (randBlessing == null) {
            val blessingCounts = blessingMapper.selectCount(new LambdaQueryWrapper<Blessing>()
                    .eq(Blessing::getStatus, BlessingStatusEnum.COMMON.getStatus()));
            if (blessingCounts == 0)
                throw new LocalException(ErrorEnum.NO_BLESSING);
            else
                return getRandBlessing(uid);
        }

        val count = likeMapper.selectCount(new LambdaQueryWrapper<Like>()
                .eq(Like::getBid, randBlessing.getId()));
        val isLiked = likeMapper.selectOne(new LambdaQueryWrapper<Like>()
                .eq(Like::getBid, randBlessing.getId())
                .eq(Like::getUid, uid)) != null;
        val response = new HashMap<String, Object>();
        response.put("id", randBlessing.getId());
        response.put("username", randBlessing.getUsername());
        response.put("blessing", randBlessing.getBlessing());
        response.put("location", randBlessing.getLocation());
        response.put("date", randBlessing.getDate());
        response.put("like", count);
        response.put("isLiked", isLiked);
        return response;
    }

    public Blessing getBlessingByBID(int bid) {
        return blessingMapper.selectOne(new LambdaQueryWrapper<Blessing>()
                .eq(Blessing::getId, bid));
    }
}
