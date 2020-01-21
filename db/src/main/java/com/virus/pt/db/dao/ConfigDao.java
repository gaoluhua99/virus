package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virus.pt.model.dataobject.Config;
import org.apache.ibatis.annotations.Select;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/14 10:32 上午
 * @email zzy.main@gmail.com
 */
public interface ConfigDao extends BaseMapper<Config> {
    @Select(value = "SELECT * FROM t_config WHERE version = (SELECT MAX(version) FROM t_config WHERE is_delete = false)")
    Config selectByVersion();
}

