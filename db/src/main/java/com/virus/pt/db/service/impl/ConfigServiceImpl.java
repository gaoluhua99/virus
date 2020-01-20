package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.dao.ConfigDao;
import com.virus.pt.db.service.ConfigService;
import com.virus.pt.model.dataobject.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/14 10:33 上午
 * @email zzy.main@gmail.com
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, Config> implements ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Override
    public void saveToRedis(Config config) {
        valueOperations.set(RedisConst.CONFIG_PREFIX, config, Duration.ofSeconds(RedisConst.CONFIG_EXP));
    }

    @Override
    public Config get() throws TipException {
        Config config = getFromRedis();
        if (config == null) {
            config = baseMapper.selectByVersion();
            if (config == null) {
                throw new TipException(ResultEnum.CONFIG_ERROR);
            } else {
                saveToRedis(config);
                logger.info("缓存Config到Redis");
            }
        }
        return config;
    }

    @Override
    public Config getFromRedis() {
        return (Config) valueOperations.get(RedisConst.CONFIG_PREFIX);
    }

    @Override
    public boolean removeFromRedis() {
        return redisTemplate.delete(RedisConst.CONFIG_PREFIX);
    }
}
