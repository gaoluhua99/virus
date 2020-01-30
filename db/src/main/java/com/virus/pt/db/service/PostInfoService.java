package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.PostInfo;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 10:06 下午
 * @email zzy.main@gmail.com
 */
public interface PostInfoService extends IService<PostInfo> {
    PostInfo getByInfoId(short infoType, long infoId);
}
