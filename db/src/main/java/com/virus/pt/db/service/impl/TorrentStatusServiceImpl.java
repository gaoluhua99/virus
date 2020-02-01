package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.virus.pt.db.dao.TorrentStatusDao;
import com.virus.pt.db.service.TorrentStatusService;
import com.virus.pt.model.dataobject.TorrentStatus;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:30 下午
 * @email zzy.main@gmail.com
 */
@Service
public class TorrentStatusServiceImpl
        extends ServiceImpl<TorrentStatusDao, TorrentStatus>
        implements TorrentStatusService {

    @Override
    public boolean exist(String userDataPasskey, long torrentId, boolean isSnatch) {
        return this.baseMapper.selectTorrentId(userDataPasskey, torrentId, isSnatch) != null;
    }

    @Override
    public boolean save(String userDataPasskey, long torrentId, boolean isSnatch, String clientName, String ip) {
        TorrentStatus torrentStatus = new TorrentStatus();
        torrentStatus.setFkUserDataPasskey(userDataPasskey);
        torrentStatus.setFkTorrentId(torrentId);
        torrentStatus.setTorrentStatus(isSnatch);
        torrentStatus.setClientName(clientName);
        torrentStatus.setIp(ip);
        return SqlHelper.retBool(this.baseMapper.insert(torrentStatus));
    }

    @Override
    public long countByTid(long torrentId) {
        return count(new QueryWrapper<TorrentStatus>()
                .eq("fk_torrent_id", torrentId)
                .eq("is_delete", false));
    }
}
