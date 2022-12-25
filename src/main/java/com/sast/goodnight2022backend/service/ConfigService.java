package com.sast.goodnight2022backend.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author NuoTian
 * @date 2022/12/25
 */
@Getter
@Service
public class ConfigService {
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${server.website}")
    private String websiteURL;
    @Value("${spring.mail.username}")
    private String mailUserName;

    public boolean isDevEnv() {
        return "dev".equals(env);
    }
}
