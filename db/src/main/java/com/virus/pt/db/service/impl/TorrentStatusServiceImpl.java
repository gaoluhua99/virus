package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.virus.pt.db.dao.TorrentStatusDao;
import com.virus.pt.db.service.TorrentStatusService;
import com.virus.pt.model.dataobject.TorrentStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public boolean exist(long tid, long time, boolean isSnatch) {
        return this.baseMapper.selectTorrentIdAndTime(tid, time, isSnatch) != null;
    }

    @Override
    public boolean save(long userDataId, long torrentId, boolean isSnatch, String clientName, String ip) {
        TorrentStatus torrentStatus = new TorrentStatus();
        torrentStatus.setFkUserDataId(userDataId);
        torrentStatus.setFkTorrentId(torrentId);
        torrentStatus.setTorrentStatus(isSnatch);
        torrentStatus.setClientName(clientName);
        torrentStatus.setIp(ip);
        torrentStatus.setCreated(new Date());
        torrentStatus.setModified(new Date());
        return SqlHelper.retBool(this.baseMapper.insert(torrentStatus));
    }
}
