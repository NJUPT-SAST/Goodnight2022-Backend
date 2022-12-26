package com.sast.goodnight2022backend.service;

import com.sast.goodnight2022backend.entity.Blessing;
import com.sast.goodnight2022backend.pojo.LikeCountCache;
import com.sast.goodnight2022backend.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author NuoTian
 * @date 2022/12/25
 */
@Slf4j
@Service
public class MailService {
    private RedisService redisService;
    private ConfigService configService;
    private BlessingService blessingService;

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    @Autowired
    public void setBlessingService(BlessingService blessingService) {
        this.blessingService = blessingService;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * 发送点赞通知
     * 一次最多发送3封
     */
    public void sendLikeCountsMail() throws MessagingException {
        if (configService.isDevEnv()) {
            log.info("测试环境跳过发送邮件");
            return;
        }

        int index = 0;
        val caches = redisService.getCachedLikeCounts();
        for (LikeCountCache cache : caches) {
            if (index >= 3)
                return;

            val duration  = Duration.between(cache.getDate(), LocalDateTime.now());
            if (duration.toHours() >= 24) {
                Integer bid   = cache.getBid();
                Blessing blessing = blessingService.getBlessingByBID(bid);
                Context context = new Context();
                context.setVariable("username", blessing.getUsername());
                context.setVariable("count", cache.getCount());
                context.setVariable("website", configService.getWebsiteURL());

                String mailContent = templateEngine.process("mail/likeCountsPlus", context);

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setFrom("SAST<" + configService.getMailUserName() + ">");
                message.setTo(blessing.getMail());
                message.setSubject("[晚安2022]点赞通知");
                message.setText(mailContent, true);
                javaMailSender.send(mimeMessage);

                log.info("已发送 {} 的点赞提醒", CommonUtil.cutString(blessing.getUid()));
                redisService.deleteLikeCountsCache(bid);
                index++;
            }
        }
    }
}
