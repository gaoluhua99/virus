package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virus.pt.model.dataobject.SeriesTorrent;
import com.virus.pt.model.dataobject.Torrent;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:22 下午
 * @email zzy.main@gmail.com
 */
public interface SeriesTorrentDao extends BaseMapper<SeriesTorrent> {
    @Select(value = "SELECT t_torrent.*\n" +
            "FROM t_series_torrent,\n" +
            "     t_torrent\n" +
            "WHERE t_series_torrent.fk_series_id = #{seriesId}\n" +
            "  AND t_series_torrent.fk_torrent_id = t_torrent.id\n" +
            "  AND t_series_torrent.is_delete = false\n" +
            "  AND t_torrent.is_delete = false")
    List<Torrent> selectTorrentListBySeriesId(long seriesId);
}
