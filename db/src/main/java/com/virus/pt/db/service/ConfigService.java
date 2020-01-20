package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.dataobject.Config;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/14 10:32 上午
 * @email zzy.main@gmail.com
 */
public interface ConfigService extends IService<Config> {
    Config get() throws TipException;

    /**
     * 获取Redis中全局配置参数
     *
     * @return 全局配置参数
     */
    Config getFromRedis();

    /**
     * 存储config
     *
     * @param config 配置
     */
    void saveToRedis(Config config);

    /**
     * 移除config
     */
    boolean removeFromRedis();
}
