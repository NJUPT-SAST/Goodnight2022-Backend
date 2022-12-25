package com.sast.goodnight2022backend.service;

import com.sast.goodnight2022backend.pojo.LikeCountCache;
import com.sast.goodnight2022backend.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author NuoTian
 * @date 2022/12/25
 */
@Slf4j
@Service
public class RedisService {
    private RedisUtil redisUtil;

    private final String LIKE_COUNTS_CODE = "LIKE:";

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void cacheLikeCountsPlus(int bid) {
        String key = LIKE_COUNTS_CODE + bid;
        if (redisUtil.hasKey(key)) {
            val cache = (LikeCountCache) redisUtil.get(key);
            cache.setCount(cache.getCount() + 1);
            redisUtil.set(key, cache);
        } else {
            val cache = new LikeCountCache();
            cache.setBid(bid);
            cache.setCount(1);
            cache.setDate(LocalDateTime.now());
            redisUtil.set(key, cache);
        }
        log.info("已更新 BID：{} 的点赞缓存（+1）", bid);
    }

    public void cacheLikeCountsMinus(int bid) {
        String key = LIKE_COUNTS_CODE + bid;
        if (redisUtil.hasKey(key)) {
            val cache = (LikeCountCache) redisUtil.get(key);
            Integer count = cache.getCount();
            if (count <= 1) {
                redisUtil.del(key);
            } else {
                cache.setCount(cache.getCount() - 1);
                redisUtil.set(key, cache);
            }
        }
        log.info("已更新 BID：{} 的点赞缓存（-1）", bid);
    }

    public ArrayList<LikeCountCache> getCachedLikeCounts() {
        Set<String> keys = redisUtil.getKeys(LIKE_COUNTS_CODE);
        val mapList = new ArrayList<LikeCountCache>();
        for (String key : keys) {
            val cache = (LikeCountCache) redisUtil.getOriginal(key);
            mapList.add(cache);
        }
        return mapList;
    }

    public void deleteLikeCountsCache(int bid) {
        redisUtil.del(LIKE_COUNTS_CODE + bid);
        log.info("已删除 BID：{} 的点赞缓存", bid);
    }
}
