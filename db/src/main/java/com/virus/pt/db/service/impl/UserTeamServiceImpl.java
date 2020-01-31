package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.UserTeamDao;
import com.virus.pt.db.service.UserTeamService;
import com.virus.pt.model.dataobject.UserTeam;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:03 下午
 * @email zzy.main@gmail.com
 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamDao, UserTeam> implements UserTeamService {
    @Override
    public UserTeam getByUserAuthId(long userAuthId) {
        return getOne(new QueryWrapper<UserTeam>()
                .eq("fk_user_auth_id", userAuthId)
                .eq("is_delete", false));
    }
}
