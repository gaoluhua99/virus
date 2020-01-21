package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.dataobject.UserAuth;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/10 11:55 上午
 * @email zzy.main@gmail.com
 */
public interface UserAuthService extends IService<UserAuth> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(UserAuth userAuth);

    boolean updateActive(String email);

    UserAuth login(String email, String password) throws TipException;

    UserAuth getByEmail(String email);

    boolean existByEmail(String email);

    boolean resetPass(String email, String password) throws TipException;
}
