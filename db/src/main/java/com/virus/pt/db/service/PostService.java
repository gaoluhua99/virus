package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.Post;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 1:05 下午
 * @email zzy.main@gmail.com
 */
public interface PostService extends IService<Post> {
    int count(String categoryName, boolean isWait);
}
