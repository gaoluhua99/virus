package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.PostCategory;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/1 11:47 上午
 * @email zzy.main@gmail.com
 */
public interface PostCategoryService extends IService<PostCategory> {
    PostCategory get(long id);
}
