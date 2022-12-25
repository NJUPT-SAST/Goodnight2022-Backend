package com.sast.goodnight2022backend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan("com.sast.goodnight2022backend.mapper")
public class GoodNight2022BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodNight2022BackendApplication.class, args);
        log.info("--------------- Started ---------------");
    }
}
