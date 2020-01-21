package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.dataobject.UserData;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 5:02 下午
 * @email zzy.main@gmail.com
 */
public interface UserDataService extends IService<UserData> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(UserData userData);

    void saveToRedis(UserData userData);

    UserData getRedisById(long id);

    UserData getRedisByPasskey(String passkey);

    UserData getById(long id) throws TipException;

    UserData getByPasskey(String passkey) throws TipException;

    boolean setStatus(String passkey, int status);

    void updateDataToRedis(String passkey, long upload, long download);
}
