package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.dataobject.Torrent;;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:16 下午
 * @email zzy.main@gmail.com
 */
public interface TorrentService extends IService<Torrent> {
    void saveToRedis(Torrent torrent);

    Torrent getRedisById(long id);

    Torrent getByTid(long id) throws TipException;

    Torrent getByHash(byte[] infoHash);

    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(Torrent torrent);
}
