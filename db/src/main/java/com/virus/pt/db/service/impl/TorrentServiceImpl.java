package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.dao.TorrentDao;
import com.virus.pt.db.service.TorrentService;
import com.virus.pt.model.dataobject.Torrent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.virus.pt.common.constant.RedisConst.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:17 下午
 * @email zzy.main@gmail.com
 */
@Service
public class TorrentServiceImpl extends ServiceImpl<TorrentDao, Torrent> implements TorrentService {
    private static final Logger logger = LoggerFactory.getLogger(TorrentServiceImpl.class);

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Override
    public void saveToRedis(Torrent torrent) {
        valueOperations.set(TORRENT_PREFIX + torrent.getId(), torrent, Duration.ofSeconds(TORRENT_EXP));
    }

    @Override
    public Torrent getRedisById(long tid) {
        return (Torrent) valueOperations.get(TORRENT_PREFIX + tid);
    }

    @Override
    public Torrent getByTid(long tid) throws TipException {
        Torrent torrent = getRedisById(tid);
        if (torrent == null) {
            torrent = getOne(new QueryWrapper<Torrent>()
                    .eq("id", tid)
                    .eq("is_delete", false));
            if (torrent == null) {
                throw new TipException(ResultEnum.NO_SUCH_SEED);
            } else {
                saveToRedis(torrent);
                logger.info("缓存Torrent到Redis: {}", torrent);
            }
        }
        return torrent;
    }

    @Override
    public Torrent getByHash(byte[] infoHash) {
        return this.getOne(new QueryWrapper<Torrent>()
                .eq("uk_info_hash", infoHash)
                .eq("is_delete", false));
    }

    @Override
    public boolean saveRollback(Torrent torrent) {
        return save(torrent);
    }
}
