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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Override
    public boolean saveRollback(UserData userData) {
        return save(userData);
    }

    @Override
    public void saveToRedis(UserData userData) {
        valueOperations.set(
                RedisConst.USER_DATA_PREFIX + userData.getId()
                        + RedisConst.REDIS_REGEX + userData.getUkPasskey(), userData,
                Duration.ofSeconds(RedisConst.USER_DATA_EXP));
    }

    @Override
    public UserData getRedisById(long id) {
        // 先获取键名再获取数据
        Set<String> keySet = redisTemplate.keys(RedisConst.USER_DATA_PREFIX + id
                + RedisConst.REDIS_REGEX + RedisConst.REDIS_ALL_KEY);
        if (keySet != null && keySet.size() > 0) {
            return (UserData) valueOperations.get(keySet.iterator().next());
        } else {
            return null;
        }
    }

    /**
     * 获取redis中存在的数据
     *
     * @return 所有用户数据集合
     */
    @Override
    public List<UserData> getListRedisById() {
        List<UserData> userDataList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisConst.USER_DATA_PREFIX + RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                UserData userData = (UserData) valueOperations.get(key);
                if (userData != null) {
                    userDataList.add(userData);
                }
            }
        }
        return userDataList;
    }

    @Override
    public UserData getRedisByPasskey(String passkey) {
        // 先获取键名再获取数据
        Set<String> keySet = redisTemplate.keys(RedisConst.USER_DATA_PREFIX + RedisConst.REDIS_ALL_KEY
                + RedisConst.REDIS_REGEX + passkey);
        if (keySet != null && keySet.size() > 0) {
            return (UserData) valueOperations.get(keySet.iterator().next());
        } else {
            return null;
        }
    }

    @Override
    public UserData getByUDId(long id) throws TipException {
        UserData userData = getRedisById(id);
        if (userData == null) {
            userData = this.getOne(new QueryWrapper<UserData>()
                    .eq("id", id)
                    .eq("is_delete", false));
            if (userData == null) {
                throw new TipException(ResultEnum.USER_EMPTY_ERROR);
            } else {
                saveToRedis(userData);
                logger.info("缓存UserData到Redis: {}", userData);
            }
        }
        userData.setModified(new Date());
        return userData;
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
        userData.setModified(new Date());
        return userData;
    }

    @Override
    public boolean setStatus(String passkey, int status) {
        UserData userData = new UserData();
        userData.setUserStatus((short) UserDataEnum.BAN.getCode());
        return this.update(userData, new UpdateWrapper<UserData>()
                .eq("uk_passkey", passkey)
                .eq("is_delete", false));
    }

    /**
     * 更新在redis中的数据
     *
     * @param passkey      passkey
     * @param uploadIncr   上传增量
     * @param downloadIncr 下载增量
     */
    @Override
    public void updateDataToRedis(String passkey, long uploadIncr, long downloadIncr) {
        UserData userData = getRedisByPasskey(passkey);
        if (userData != null) {
            userData.setUploaded(userData.getUploaded() + uploadIncr);
            userData.setDownloaded(userData.getDownloaded() + downloadIncr);
            userData.setModified(new Date());
            saveToRedis(userData);
        }
    }

    /**
     * 更新在数据库中的数据
     *
     * @param passkey  passkey
     * @param upload   上传量(不是增量)
     * @param download 下载量(不是增量)
     * @return
     */
    @Override
    public boolean updateData(String passkey, long upload, long download) {
//        updateDataToRedis(passkey, upload, download);
        UserData userData = new UserData();
        userData.setUploaded(upload);
        userData.setDownloaded(download);
        return this.update(userData, new UpdateWrapper<UserData>()
                .eq("uk_passkey", passkey)
                .eq("is_delete", false));
    }

    @Override
    public int countByStatus(short status) {
        return count(new QueryWrapper<UserData>()
                .eq("user_status", status)
                .eq("is_delete", false));
    }

    @Override
    public void removeByRedis(long id, String passkey) {
        redisTemplate.delete(RedisConst.USER_DATA_PREFIX + id + RedisConst.REDIS_REGEX + passkey);
    }
}
