package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.UserData;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 5:02 下午
 * @email zzy.main@gmail.com
 */
public interface UserDataService extends IService<UserData> {
    UserData getByPasskey(String passkey);
}
