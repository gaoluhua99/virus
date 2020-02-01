package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.TorrentStatus;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:29 下午
 * @email zzy.main@gmail.com
 */
public interface TorrentStatusService extends IService<TorrentStatus> {
    boolean exist(String userDataPasskey, long torrentId, boolean isSnatch);

    boolean save(String userDataPasskey, long torrentId, boolean isSnatch, String clientName, String ip);

    long countByTid(long torrentId);
}
