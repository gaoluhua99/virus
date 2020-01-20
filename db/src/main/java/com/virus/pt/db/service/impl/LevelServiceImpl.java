package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.LevelDao;
import com.virus.pt.db.service.LevelService;
import com.virus.pt.model.dataobject.UserLevel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 3:58 下午
 * @email zzy.main@gmail.com
 */
@Service
public class LevelServiceImpl extends ServiceImpl<LevelDao, UserLevel> implements LevelService {

    @Override
    public List<UserLevel> getAll() {
        return baseMapper.selectAll();
    }
}
