package com.virus.pt.db.service.impl;

import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.service.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 1:51 下午
 * @email zzy.main@gmail.com
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Override
    public void saveCaptcha(String ip, String captcha) throws TipException {
        if (StringUtils.isBlank(captcha)) {
            throw new TipException(ResultEnum.CAPTCHA_EMPTY_ERROR);
        }
        valueOperations.set(RedisConst.CAPTCHA_PREFIX + ip, captcha, Duration.ofSeconds(RedisConst.CAPTCHA_EXP));
    }

    @Override
    public boolean removeCaptcha(String ip) {
        return redisTemplate.delete(RedisConst.CAPTCHA_PREFIX + ip);
    }

    @Override
    public boolean verifyCaptcha(String ip, String captcha) throws TipException {
        if (StringUtils.isBlank(captcha)) {
            throw new TipException(ResultEnum.CAPTCHA_EMPTY_ERROR);
        }
        if (captcha.length() != 4) {
            throw new TipException(ResultEnum.CAPTCHA_FORMAT_ERROR);
        }
        String verifyCaptcha = (String) valueOperations.get(RedisConst.CAPTCHA_PREFIX + ip);
        return StringUtils.isNotBlank(verifyCaptcha) && verifyCaptcha.equals(captcha.toUpperCase());
    }
}
