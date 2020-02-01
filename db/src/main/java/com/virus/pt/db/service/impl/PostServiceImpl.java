package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.PostDao;
import com.virus.pt.db.service.PostService;
import com.virus.pt.model.dataobject.Post;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 1:09 下午
 * @email zzy.main@gmail.com
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private IPage<Post> getPostPageDesc(int pageIndex, int pageSize, boolean desc, String field, boolean wait) {
        Page<Post> postPage = new Page<>(pageIndex, pageSize);
        if (desc) {
            return page(postPage, new QueryWrapper<Post>()
                    .eq("is_wait", wait)
                    .eq("is_delete", false))
                    .addOrder(OrderItem.desc("hot"))
                    .addOrder(OrderItem.desc("pin"))
                    .addOrder(OrderItem.desc(field));
        } else {
            return page(postPage, new QueryWrapper<Post>()
                    .eq("is_wait", wait)
                    .eq("is_delete", false))
                    .addOrder(OrderItem.desc("hot"))
                    .addOrder(OrderItem.desc("pin"))
                    .addOrder(OrderItem.asc(field));
        }
    }

    @Override
    public IPage<Post> getPostPage(int pageIndex, int pageSize, boolean wait) {
        return getPostPageDesc(pageIndex, pageSize, true, "modified", wait);
    }

    @Override
    public IPage<Post> getPostPageOrderByCreated(int pageIndex, int pageSize, boolean desc, boolean wait) {
        return getPostPageDesc(pageIndex, pageSize, desc, "created", wait);
    }

    @Override
    public IPage<Post> getPostPageOrderByUid(int pageIndex, int pageSize, boolean desc, boolean wait) {
        return getPostPageDesc(pageIndex, pageSize, desc, "fk_user_auth_id", wait);
    }

    @Override
    public IPage<Post> getPostPageOrderBySize(int pageIndex, int pageSize, boolean desc, boolean wait) {
        Page<Post> postPage = new Page<>(pageIndex, pageSize);
        if (desc) {
            return baseMapper.selectPostPageOrderBySizeDesc(postPage, wait);
        }
        return baseMapper.selectPostPageOrderBySizeAsc(postPage, wait);
    }

    @Override
    public IPage<Post> getPostPageOrderByTitle(int pageIndex, int pageSize, boolean desc, boolean wait) {
        return getPostPageDesc(pageIndex, pageSize, desc, "title", wait);
    }

    @Override
    public IPage<Post> getPostPageByCategoryName(int pageIndex, int pageSize, String categoryName, boolean wait) {
        return getPostPageByCategoryNameOrder(pageIndex, pageSize, categoryName, true, "modified", wait);
    }

    private IPage<Post> getPostPageByCategoryNameOrder(int pageIndex, int pageSize, String categoryName,
                                                       boolean desc, String field, boolean wait) {
        Page<Post> postPage = new Page<>(pageIndex, pageSize);
        if (desc) {
            return page(postPage, new QueryWrapper<Post>()
                    .eq("category_name", categoryName)
                    .eq("is_wait", wait)
                    .eq("is_delete", false))
                    .addOrder(OrderItem.desc("hot"))
                    .addOrder(OrderItem.desc("pin"))
                    .addOrder(OrderItem.desc(field));
        } else {
            return page(postPage, new QueryWrapper<Post>()
                    .eq("category_name", categoryName)
                    .eq("is_wait", wait)
                    .eq("is_delete", false))
                    .addOrder(OrderItem.desc("hot"))
                    .addOrder(OrderItem.desc("pin"))
                    .addOrder(OrderItem.asc(field));
        }
    }

    @Override
    public IPage<Post> getPostPageByCategoryNameOrderByCreated(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait) {
        return getPostPageByCategoryNameOrder(pageIndex, pageSize, categoryName, desc, "created", wait);
    }

    @Override
    public IPage<Post> getPostPageByCategoryNameOrderByUid(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait) {
        return getPostPageByCategoryNameOrder(pageIndex, pageSize, categoryName, desc, "fk_user_auth_id", wait);
    }

    @Override
    public IPage<Post> getPostPageByCategoryNameOrderByTitle(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait) {
        return getPostPageByCategoryNameOrder(pageIndex, pageSize, categoryName, desc, "title", wait);
    }

    @Override
    public IPage<Post> getPostPageByCategoryNameOrderBySize(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait) {
        Page<Post> postPage = new Page<>(pageIndex, pageSize);
        if (desc) {
            return baseMapper.selectPostPageByCategoryNameOrderBySizeDesc(postPage, categoryName, wait);
        }
        return baseMapper.selectPostPageByCategoryNameOrderBySizeAsc(postPage, categoryName, wait);
    }

    @Override
    public int count(String categoryName, boolean isWait) {
        if (StringUtils.isBlank(categoryName)) {
            return count(new QueryWrapper<Post>()
                    .eq("is_wait", isWait)
                    .eq("is_delete", false));
        }
        return count(new QueryWrapper<Post>()
                .eq("category_name", categoryName)
                .eq("is_wait", isWait)
                .eq("is_delete", false));
    }

    @Override
    public boolean saveRollback(Post post) {
        return save(post);
    }
}
