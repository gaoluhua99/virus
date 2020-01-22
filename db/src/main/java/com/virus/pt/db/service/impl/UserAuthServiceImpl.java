package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.CheckUtils;
import com.virus.pt.common.util.VirusUtils;
import com.virus.pt.db.dao.UserAuthDao;
import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.model.dataobject.UserAuth;
import org.apache.commons.codec.digest.DigestUtils;
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

    @Override
    public UserAuth login(String email, String password) throws TipException {
        UserAuth userAuth = this.getOne(new QueryWrapper<UserAuth>()
                .eq("uk_email", email)
                .eq("password_hash", DigestUtils.md5Hex(VirusUtils.config.getPassPrefix() + password))
                .eq("is_delete", false));
        if (userAuth == null) {
            throw new TipException(ResultEnum.LOGIN_ERROR);
        } else if (!userAuth.getIsActivation()) {
            throw new TipException(ResultEnum.ACTIVATION_ERROR);
        } else {
            return userAuth;
        }
    }

    @Override
    public UserAuth getByEmail(String email) throws TipException {
        UserAuth userAuth = getOne(new QueryWrapper<UserAuth>()
                .eq("uk_email", email)
                .eq("is_delete", false));
        if (userAuth == null) {
            throw new TipException(ResultEnum.USER_EMPTY_ERROR);
        } else {
            return userAuth;
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return getOne(new QueryWrapper<UserAuth>()
                .eq("uk_email", email)
                .eq("is_delete", false)) != null;
    }

    @Override
    public UserAuth getById(long id) throws TipException {
        UserAuth userAuth = this.getOne(new QueryWrapper<UserAuth>()
                .eq("id", id)
                .eq("is_delete", false));
        if (userAuth == null) {
            throw new TipException(ResultEnum.USER_EMPTY_ERROR);
        } else {
            return userAuth;
        }
    }

    @Override
    public boolean resetPass(String email, String password) throws TipException {
        UserAuth userAuth = new UserAuth();
        CheckUtils.checkPassword(password);
        userAuth.setPasswordHash(DigestUtils.md5Hex(VirusUtils.config.getPassPrefix() + password));
        return this.update(userAuth, new UpdateWrapper<UserAuth>()
                .eq("uk_email", email)
                .eq("is_delete", false));
    }
}
