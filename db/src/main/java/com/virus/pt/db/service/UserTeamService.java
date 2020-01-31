package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.UserTeam;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:02 下午
 * @email zzy.main@gmail.com
 */
public interface UserTeamService extends IService<UserTeam> {
    UserTeam getByUserAuthId(long userAuthId);
}
