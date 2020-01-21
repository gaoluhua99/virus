package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virus.pt.model.dataobject.UserInfo;
import org.apache.ibatis.annotations.Select;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 2:45 下午
 * @email zzy.main@gmail.com
 */
public interface UserInfoDao extends BaseMapper<UserInfo> {
    @Select(value = "SELECT id, created, modified, fk_user_auth_id, fk_user_data_id, uk_username, sex, gold, exp, inviter_id, avatar_url, INET6_NTOA(ip) as ip FROM t_user_info WHERE fk_user_auth_id = #{userAuthId} AND is_delete = false")
    UserInfo selectByUserAuthId(long userAuthId);
}
