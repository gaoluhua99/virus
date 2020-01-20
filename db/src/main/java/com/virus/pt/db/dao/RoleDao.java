package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virus.pt.model.dataobject.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/14 10:32 上午
 * @email zzy.main@gmail.com
 */
public interface RoleDao extends BaseMapper<Role> {
    @Select(value = "SELECT t_role.* FROM t_user_auth, t_role, t_user_role WHERE t_user_auth.id = #{userAuthId} AND t_user_auth.id = t_user_role.fk_user_auth_id AND t_role.id = t_user_role.fk_role_id")
    List<Role> selectByUserAuthId(long userAuthId);
}

