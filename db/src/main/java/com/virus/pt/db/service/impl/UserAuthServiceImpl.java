package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.UserAuthDao;
import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.model.dataobject.UserAuth;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/10 11:56 上午
 * @email zzy.main@gmail.com
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth> implements UserAuthService {

    @Override
    public boolean saveRollback(UserAuth userAuth) {
        return save(userAuth);
    }

    @Override
    public boolean updateActive(String email) {
        UserAuth userAuth = new UserAuth();
        userAuth.setIsActivation(true);
        return this.update(userAuth, new UpdateWrapper<UserAuth>()
                .eq("uk_email", email)
                .eq("is_delete", false));
    }
}
