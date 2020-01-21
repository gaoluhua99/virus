package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.dao.UserInfoDao;
import com.virus.pt.db.service.UserInfoService;
import com.virus.pt.model.dataobject.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 2:46 下午
 * @email zzy.main@gmail.com
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {
    @Override
    public boolean saveRollback(UserInfo userInfo) {
        return save(userInfo);
    }

    @Override
    public UserInfo getByUserAuthId(long userAuthId) throws TipException {
        UserInfo userInfo = baseMapper.selectByUserAuthId(userAuthId);
        if (userInfo == null) {
            throw new TipException(ResultEnum.USER_EMPTY_ERROR);
        }
        return userInfo;
    }
}
