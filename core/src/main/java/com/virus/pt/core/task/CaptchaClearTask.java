package com.virus.pt.core.task;

import com.virus.pt.common.constant.TaskConst;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.core.service.impl.CaptchaStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/22 2:42 下午
 * @email zzy.main@gmail.com
 */
@Configuration
@EnableScheduling
public class CaptchaClearTask {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaClearTask.class);

    @Autowired
    private CaptchaStorageService captchaStorageService;

    //    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(fixedRate = TaskConst.CLEAR_CAPTCHA_RATE)
    private void clearCaptcha() throws TipException {
        logger.info("正在清空验证码图片目录, 结果: {}", captchaStorageService.deleteAll());
        captchaStorageService.init();
    }
}
