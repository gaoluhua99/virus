package com.virus.pt.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.Post;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 1:05 下午
 * @email zzy.main@gmail.com
 */
public interface PostService extends IService<Post> {
    IPage<Post> getPostPage(int pageIndex, int pageSize, boolean wait);

    IPage<Post> getPostPageOrderByTitle(int pageIndex, int pageSize, boolean desc, boolean wait);

    IPage<Post> getPostPageOrderByCreated(int pageIndex, int pageSize, boolean desc, boolean wait);

    IPage<Post> getPostPageOrderByUid(int pageIndex, int pageSize, boolean desc, boolean wait);

    IPage<Post> getPostPageOrderBySize(int pageIndex, int pageSize, boolean desc, boolean wait);

    IPage<Post> getPostPageByCategoryName(int pageIndex, int pageSize, String categoryName, boolean wait);

    IPage<Post> getPostPageByCategoryNameOrderByCreated(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait);

    IPage<Post> getPostPageByCategoryNameOrderByUid(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait);

    IPage<Post> getPostPageByCategoryNameOrderByTitle(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait);

    IPage<Post> getPostPageByCategoryNameOrderBySize(int pageIndex, int pageSize, String categoryName, boolean desc, boolean wait);

    int count(String categoryName, boolean isWait);

    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(Post post);
}
