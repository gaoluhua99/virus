package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.UserDataDao;
import com.virus.pt.db.service.UserDataService;
import com.virus.pt.model.dataobject.UserData;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 5:03 下午
 * @email zzy.main@gmail.com
 */
@Service
public class UserDataServiceImpl extends ServiceImpl<UserDataDao, UserData> implements UserDataService {

    @Override
    public UserData getByPasskey(String passkey) {
        return this.getOne(new QueryWrapper<UserData>().eq("uk_passkey", passkey));
    }
}
