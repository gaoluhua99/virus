package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.PostContentDao;
import com.virus.pt.db.service.PostContentService;
import com.virus.pt.model.dataobject.PostContent;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:07 下午
 * @email zzy.main@gmail.com
 */
@Service
public class PostContentServiceImpl extends ServiceImpl<PostContentDao, PostContent> implements PostContentService {
    @Override
    public boolean saveRollback(PostContent postContent) {
        return save(postContent);
    }

    @Override
    public PostContent get(long id) {
        return getOne(new QueryWrapper<PostContent>()
                .eq("id", id)
                .eq("is_delete", false));
    }
}
