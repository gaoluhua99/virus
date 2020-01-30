package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.PostDao;
import com.virus.pt.db.service.PostService;
import com.virus.pt.model.dataobject.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 1:09 下午
 * @email zzy.main@gmail.com
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {
    @Override
    public int count(String categoryName, boolean isWait) {
        if (StringUtils.isBlank(categoryName)) {
            return count(new QueryWrapper<Post>()
                    .eq("is_wait", isWait)
                    .eq("is_delete", false));
        }
        return baseMapper.countByCategoryName(categoryName, isWait);
    }
}
