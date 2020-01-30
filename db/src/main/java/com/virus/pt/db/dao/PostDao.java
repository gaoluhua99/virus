package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virus.pt.model.dataobject.Post;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 1:04 下午
 * @email zzy.main@gmail.com
 */
public interface PostDao extends BaseMapper<Post> {
    @Select(value = "select count(*) from t_posts, t_post_category where t_posts.fk_post_category_id = t_post_category.id and t_post_category.category_name = '${categoryName}' and is_wait = ${isWait} and is_delete = false")
    int countByCategoryName(@Param("categoryName") String categoryName, @Param("isWait") boolean isWait);
}
