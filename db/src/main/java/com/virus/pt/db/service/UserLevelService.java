package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.UserLevel;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 9:04 下午
 * @email zzy.main@gmail.com
 */
public interface UserLevelService extends IService<UserLevel> {
    List<UserLevel> getAll();
}
