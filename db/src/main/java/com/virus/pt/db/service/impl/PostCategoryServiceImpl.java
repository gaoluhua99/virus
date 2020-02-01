package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.PostCategoryDao;
import com.virus.pt.db.service.PostCategoryService;
import com.virus.pt.model.dataobject.PostCategory;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/1 11:47 上午
 * @email zzy.main@gmail.com
 */
@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryDao, PostCategory> implements PostCategoryService {
    @Override
    public PostCategory get(long id) {
        return getOne(new QueryWrapper<PostCategory>()
                .eq("id", id)
                .eq("is_delete", false));
    }
}
