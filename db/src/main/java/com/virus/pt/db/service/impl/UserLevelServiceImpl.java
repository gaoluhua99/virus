package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.UserLevelDao;
import com.virus.pt.db.service.UserLevelService;
import com.virus.pt.model.dataobject.UserLevel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 9:04 下午
 * @email zzy.main@gmail.com
 */
@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelDao, UserLevel> implements UserLevelService {

    @Override
    public List<UserLevel> getAll() {
        return baseMapper.selectAll();
    }
}
