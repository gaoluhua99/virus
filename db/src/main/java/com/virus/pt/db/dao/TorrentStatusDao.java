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
    @Insert(value = "INSERT INTO t_torrent_status (created, modified, fk_user_data_passkey, fk_torrent_id, torrent_status, ip, client_name) VALUES (#{created}, #{modified}, #{fkUserDataPasskey}, #{fkTorrentId}, #{torrentStatus}, INET6_ATON(#{ip}), #{clientName})")
    int insert(TorrentStatus torrentStatus);

    @Select(value = "SELECT id, created, modified, fk_user_data_passkey, fk_torrent_id, torrent_status, INET6_NTOA(ip) as ip, client_name FROM t_torrent_status WHERE fk_user_data_passkey=#{userDataPasskey} AND fk_torrent_id=#{torrentId} AND torrent_status=#{torrentStatus} AND is_delete=false")
    TorrentStatus selectTorrentId(String userDataPasskey, long torrentId, boolean torrentStatus);

    @Select(value = "SELECT id, created, modified, fk_user_data_passkey, fk_torrent_id, torrent_status, INET6_NTOA(ip) as ip, client_name FROM t_torrent_status WHERE fk_torrent_id=#{torrentId} AND is_delete=false")
    List<TorrentStatus> selectTorrentIdList(long torrentId);

    @Select(value = "SELECT id, created, modified, fk_user_data_passkey, fk_torrent_id, torrent_status, INET6_NTOA(ip) as ip, client_name FROM t_torrent_status WHERE fk_user_data_passkey=#{userDataPasskey} AND is_delete=false")
    List<TorrentStatus> selectUserDataPasskeyList(String userDataPasskey);
}
