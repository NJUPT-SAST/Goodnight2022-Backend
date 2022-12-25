package com.sast.goodnight2022backend;

import com.sast.goodnight2022backend.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
class GoodNight2022BackendApplicationTests {
    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        val cache = redisService.getCachedLikeCounts();
        log.info(cache.toString());
    }

}
