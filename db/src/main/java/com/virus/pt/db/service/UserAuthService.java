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

    @Transactional(rollbackFor = Exception.class)
    boolean updateActive(String email);

    UserAuth login(String email, String password) throws TipException;

    UserAuth getByEmail(String email) throws TipException;

    boolean existByEmail(String email);

    UserAuth getById(long id) throws TipException;

    @Transactional(rollbackFor = Exception.class)
    boolean resetPass(String email, String password) throws TipException;

    int countTotal();

    int countNotActivation();
}
