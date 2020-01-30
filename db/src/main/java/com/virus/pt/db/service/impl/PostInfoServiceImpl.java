package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.PostInfoDao;
import com.virus.pt.db.service.PostInfoService;
import com.virus.pt.model.dataobject.PostInfo;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 10:08 下午
 * @email zzy.main@gmail.com
 */
@Service
public class PostInfoServiceImpl extends ServiceImpl<PostInfoDao, PostInfo> implements PostInfoService {
    @Override
    public PostInfo getByInfoId(short infoType, long infoId) {
        return this.getOne(new QueryWrapper<PostInfo>()
                .eq("uk_info_id", infoId)
                .eq("info_type", infoType)
                .eq("is_delete", false));
    }
}
