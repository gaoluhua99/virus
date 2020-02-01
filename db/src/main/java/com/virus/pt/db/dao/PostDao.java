package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    // 大小降序
    @Select(value = "SELECT sum(t_torrent.torrent_size) as size,\n" +
            "       t_post.id,\n" +
            "       t_post.created,\n" +
            "       t_post.modified,\n" +
            "       t_post.fk_user_auth_id,\n" +
            "       t_post.fk_user_team_id,\n" +
            "       pin,\n" +
            "       title,\n" +
            "       subtitle,\n" +
            "       hot,\n" +
            "       imdb_id,\n" +
            "       douban_id,\n" +
            "       category_name,\n" +
            "       quality\n" +
            "FROM t_post,\n" +
            "     t_series,\n" +
            "     t_series_torrent,\n" +
            "     t_torrent\n" +
            "WHERE t_post.id = t_series.fk_post_id\n" +
            "  AND t_series.id = t_series_torrent.fk_series_id\n" +
            "  AND t_series_torrent.fk_torrent_id = t_torrent.id\n" +
            "  AND is_wait = #{isWait}\n" +
            "  AND t_post.is_delete = false\n" +
            "  AND t_series.is_delete = false\n" +
            "  AND t_series_torrent.is_delete = false\n" +
            "  AND t_torrent.is_delete = false\n" +
            "group by t_post.id\n" +
            "order by pin desc, hot desc, size desc")
    IPage<Post> selectPostPageOrderBySizeDesc(Page page, @Param("isWait") boolean isWait);

    // 大小升序
    @Select(value = "SELECT sum(t_torrent.torrent_size) as size,\n" +
            "       t_post.id,\n" +
            "       t_post.created,\n" +
            "       t_post.modified,\n" +
            "       t_post.fk_user_auth_id,\n" +
            "       t_post.fk_user_team_id,\n" +
            "       pin,\n" +
            "       title,\n" +
            "       subtitle,\n" +
            "       hot,\n" +
            "       imdb_id,\n" +
            "       douban_id,\n" +
            "       category_name,\n" +
            "       quality\n" +
            "FROM t_post,\n" +
            "     t_series,\n" +
            "     t_series_torrent,\n" +
            "     t_torrent\n" +
            "WHERE t_post.id = t_series.fk_post_id\n" +
            "  AND t_series.id = t_series_torrent.fk_series_id\n" +
            "  AND t_series_torrent.fk_torrent_id = t_torrent.id\n" +
            "  AND is_wait = #{isWait}\n" +
            "  AND t_post.is_delete = false\n" +
            "  AND t_series.is_delete = false\n" +
            "  AND t_series_torrent.is_delete = false\n" +
            "  AND t_torrent.is_delete = false\n" +
            "group by t_post.id\n" +
            "order by pin desc, hot desc, size asc")
    IPage<Post> selectPostPageOrderBySizeAsc(Page page, @Param("isWait") boolean isWait);

    // 分类按size降序
    @Select(value = "SELECT sum(t_torrent.torrent_size) as size,\n" +
            "       t_post.id,\n" +
            "       t_post.created,\n" +
            "       t_post.modified,\n" +
            "       t_post.fk_user_auth_id,\n" +
            "       t_post.fk_user_team_id,\n" +
            "       pin,\n" +
            "       title,\n" +
            "       subtitle,\n" +
            "       hot,\n" +
            "       imdb_id,\n" +
            "       douban_id,\n" +
            "       category_name,\n" +
            "       quality\n" +
            "FROM t_post,\n" +
            "     t_series,\n" +
            "     t_series_torrent,\n" +
            "     t_torrent\n" +
            "WHERE t_post.id = t_series.fk_post_id\n" +
            "  AND t_series.id = t_series_torrent.fk_series_id\n" +
            "  AND t_series_torrent.fk_torrent_id = t_torrent.id\n" +
            "  AND t_post.category_name = '#{categoryName}'\n" +
            "  AND is_wait = #{isWait}\n" +
            "  AND t_post.is_delete = false\n" +
            "  AND t_series.is_delete = false\n" +
            "  AND t_series_torrent.is_delete = false\n" +
            "  AND t_torrent.is_delete = false\n" +
            "group by t_post.id\n" +
            "order by pin desc, hot desc, size desc")
    IPage<Post> selectPostPageByCategoryNameOrderBySizeDesc(Page page, @Param("categoryName") String categoryName, @Param("isWait") boolean isWait);

    // 分类按size升序
    @Select(value = "SELECT sum(t_torrent.torrent_size) as size,\n" +
            "       t_post.id,\n" +
            "       t_post.created,\n" +
            "       t_post.modified,\n" +
            "       t_post.fk_user_auth_id,\n" +
            "       t_post.fk_user_team_id,\n" +
            "       pin,\n" +
            "       title,\n" +
            "       subtitle,\n" +
            "       hot,\n" +
            "       imdb_id,\n" +
            "       douban_id,\n" +
            "       category_name,\n" +
            "       quality\n" +
            "FROM t_post,\n" +
            "     t_series,\n" +
            "     t_series_torrent,\n" +
            "     t_torrent\n" +
            "WHERE t_post.id = t_series.fk_post_id\n" +
            "  AND t_series.id = t_series_torrent.fk_series_id\n" +
            "  AND t_series_torrent.fk_torrent_id = t_torrent.id\n" +
            "  AND t_post.category_name = '#{categoryName}'\n" +
            "  AND is_wait = #{isWait}\n" +
            "  AND t_post.is_delete = false\n" +
            "  AND t_series.is_delete = false\n" +
            "  AND t_series_torrent.is_delete = false\n" +
            "  AND t_torrent.is_delete = false\n" +
            "group by t_post.id\n" +
            "order by pin desc, hot desc, size asc")
    IPage<Post> selectPostPageByCategoryNameOrderBySizeAsc(Page page, @Param("categoryName") String categoryName, @Param("isWait") boolean isWait);
}
