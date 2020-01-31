package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.PostContent;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:07 下午
 * @email zzy.main@gmail.com
 */
public interface PostContentService extends IService<PostContent> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(PostContent postContent);
}
