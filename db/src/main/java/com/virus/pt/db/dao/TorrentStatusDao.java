package com.virus.pt.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virus.pt.model.dataobject.TorrentStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:28 下午
 * @email zzy.main@gmail.com
 */
public interface TorrentStatusDao extends BaseMapper<TorrentStatus> {
    @Insert(value = "INSERT INTO t_torrent_status (created, modified, fk_user_data_id, fk_torrent_id, torrent_status, ip, client_name) VALUES (#{created}, #{modified}, #{fkUserDataId}, #{fkTorrentId}, #{torrentStatus}, INET6_ATON(#{ip}), #{clientName})")
    int insert(TorrentStatus torrentStatus);

    @Select(value = "SELECT id, created, modified, fk_user_data_id, fk_torrent_id, torrent_status, INET6_NTOA(ip) as ip, client_name FROM t_torrent_status WHERE fk_torrent_id=#{torrentId} AND created >= DATE_SUB(now(), INTERVAL #{time} SECOND) AND torrent_status=#{torrentStatus}")
    TorrentStatus selectTorrentIdAndTime(long torrentId, long time, boolean torrentStatus);

    @Select(value = "SELECT id, created, modified, fk_user_data_id, fk_torrent_id, torrent_status, INET6_NTOA(ip) as ip, client_name FROM t_torrent_status WHERE fk_torrent_id=#{torrentId}")
    List<TorrentStatus> selectTorrentId(long torrentId);

    @Select(value = "SELECT id, created, modified, fk_user_data_id, fk_torrent_id, torrent_status, INET6_NTOA(ip) as ip, client_name FROM t_torrent_status WHERE fk_user_data_id=#{userDataId}")
    List<TorrentStatus> selectUserDataId(long userDataId);
}
