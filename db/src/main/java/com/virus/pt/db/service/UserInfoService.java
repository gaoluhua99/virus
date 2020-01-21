package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.UserInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 2:45 下午
 * @email zzy.main@gmail.com
 */
public interface UserInfoService extends IService<UserInfo> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(UserInfo userInfo);
}
