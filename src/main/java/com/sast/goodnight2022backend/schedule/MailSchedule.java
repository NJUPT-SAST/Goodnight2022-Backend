package com.sast.goodnight2022backend.schedule;

import com.sast.goodnight2022backend.service.MailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author NuoTian
 * @date 2022/12/25
 */
@Slf4j
@Component
public class MailSchedule {
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 每10分钟检查一次是否需要发送邮件
     */
    @Scheduled(fixedDelay = 10*60*1000)
    public void sendLikeCountsMailSchedule() {
        try {
            mailService.sendLikeCountsMail();
        } catch (MessagingException e) {
            log.error("发送邮件时发生错误：", e);
        }
    }
}
