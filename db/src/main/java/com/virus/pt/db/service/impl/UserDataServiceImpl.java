package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.enums.UserDataEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.dao.UserDataDao;
import com.virus.pt.db.service.UserDataService;
import com.virus.pt.model.dataobject.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 5:03 下午
 * @email zzy.main@gmail.com
 */
@Service
public class UserDataServiceImpl extends ServiceImpl<UserDataDao, UserData> implements UserDataService {
    private static final Logger logger = LoggerFactory.getLogger(UserDataServiceImpl.class);

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Override
    public void saveToRedis(UserData userData) {
        valueOperations.set(RedisConst.USER_DATA_PREFIX + userData.getUkPasskey(), userData,
                Duration.ofSeconds(RedisConst.USER_DATA_EXP));
    }

    @Override
    public UserData getRedisByPasskey(String passkey) {
        return (UserData) valueOperations.get(RedisConst.USER_DATA_PREFIX + passkey);
    }

    @Override
    public UserData getByPasskey(String passkey) throws TipException {
        UserData userData = getRedisByPasskey(passkey);
        if (userData == null) {
            userData = this.getOne(new QueryWrapper<UserData>()
                    .eq("uk_passkey", passkey)
                    .eq("is_delete", false));
            if (userData == null) {
                throw new TipException(ResultEnum.USER_EMPTY_ERROR);
            } else {
                saveToRedis(userData);
                logger.info("缓存UserData到Redis: {}", userData);
            }
        }
        return userData;
    }

    @Override
    public boolean setStatus(String passkey, int status) {
        UserData userData = new UserData();
        userData.setUserStatus((short) UserDataEnum.BAN.getCode());
        return this.update(userData, new UpdateWrapper<UserData>()
                .eq("uk_passkey", passkey));
    }

    @Override
    public void updateDataToRedis(String passkey, long upload, long download) {
        UserData userData = getRedisByPasskey(passkey);
        userData.setUploaded(userData.getUploaded() + upload);
        userData.setDownloaded(userData.getDownloaded() + download);
        saveToRedis(userData);
    }
}
