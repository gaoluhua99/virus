package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.Role;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 3:58 下午
 * @email zzy.main@gmail.com
 */
public interface RoleService extends IService<Role> {
    List<Role> getByUserAuthId(long userAuthId);
}
