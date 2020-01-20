package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.RoleDao;
import com.virus.pt.db.service.RoleService;
import com.virus.pt.model.dataobject.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 3:58 下午
 * @email zzy.main@gmail.com
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Override
    public List<Role> getByUserAuthId(long userAuthId) {
        return baseMapper.selectByUserAuthId(userAuthId);
    }
}
