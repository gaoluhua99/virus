package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.UserLevel;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 3:58 下午
 * @email zzy.main@gmail.com
 */
public interface LevelService extends IService<UserLevel> {
    List<UserLevel> getAll();
}
