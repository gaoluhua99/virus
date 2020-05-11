package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.dataobject.UserData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 获取redis中存在的数据
     *
     * @return 所有用户数据集合
     */
    List<UserData> getListRedisById();

    UserData getRedisByPasskey(String passkey);

    UserData getByUDId(long id) throws TipException;

    UserData getByPasskey(String passkey) throws TipException;

    boolean setStatus(String passkey, int status);

    /**
     * 更新在redis中的数据
     *
     * @param passkey      passkey
     * @param uploadIncr   上传增量
     * @param downloadIncr 下载增量
     */
    void updateDataToRedis(String passkey, long uploadIncr, long downloadIncr);

    /**
     * 更新在数据库中的数据
     *
     * @param passkey  passkey
     * @param upload   上传量(不是增量)
     * @param download 下载量(不是增量)
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateData(String passkey, long upload, long download);

    int countByStatus(short status);

    /**
     * 删除redis中的数据
     *
     * @param id      user data id
     * @param passkey passkey
     */
    void removeByRedis(long id, String passkey);
}
