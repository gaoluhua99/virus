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
    // 是否存在时间小于time的数据
    boolean exist(long tid, long time,boolean isSnatch);

    boolean save(long userDataId, long torrentId, boolean isSnatch, String clientName, String ip);
}
