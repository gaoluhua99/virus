package com.virus.pt.db.service.impl;

import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.common.enums.PeerStateEnum;
import com.virus.pt.db.service.PeerService;
import com.virus.pt.model.dataobject.Peer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 4:39 下午
 * @email zzy.main@gmail.com
 */
@Service
public class PeerServiceImpl implements PeerService {
    private static final Logger logger = LoggerFactory.getLogger(PeerServiceImpl.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Autowired
    private ValueOperations valueOperations;

    @Override
    public void save(long tid, Peer peer) {
        valueOperations.set(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId(), peer, Duration.ofSeconds(RedisConst.PEER_EXP));
        if (peer.getState() == PeerStateEnum.SEEDING.getCode()) {
            zSetOperations.incrementScore(RedisConst.RANK_SEEDING_PREFIX, tid, 1);
        } else {
            zSetOperations.incrementScore(RedisConst.RANK_DOWNLOADING_PREFIX, tid, 1);
        }
    }

    @Override
    public void saveAll(long tid, List<Peer> peerList) {
        peerList.forEach(peer -> {
            save(tid, peer);
        });
    }

    @Override
    public Peer get(long tid, Peer peer) {
        return (Peer) valueOperations.get(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId());
    }

    @Override
    public List<Peer> getList(long tid) {
        List<Peer> peerList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisConst.PEER_PREFIX + tid + RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                peerList.add((Peer) valueOperations.get(key));
            }
        }
        return peerList;
    }

    @Override
    public void remove(long tid, Peer peer) {
        redisTemplate.delete(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId());
        if (peer.getState() == PeerStateEnum.SEEDING.getCode()) {
            zSetOperations.incrementScore(RedisConst.RANK_SEEDING_PREFIX, tid, -1);
        } else {
            zSetOperations.incrementScore(RedisConst.RANK_DOWNLOADING_PREFIX, tid, -1);
        }
    }

    @Override
    public long getCount(long tid) {
        Set<String> keys = redisTemplate.keys(RedisConst.PEER_PREFIX + tid + RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() > 0) {
            return keys.size();
        }
        return 0;
    }

    @Override
    public long getDownloadingCount(long tid) {
        Double count = zSetOperations.score(RedisConst.RANK_DOWNLOADING_PREFIX, tid);
        return count == null ? 0 : count.longValue();
    }

    @Override
    public long getSeedingCount(long tid) {
        Double count = zSetOperations.score(RedisConst.RANK_SEEDING_PREFIX, tid);
        return count == null ? 0 : count.longValue();
    }

    @Override
    public List<Long> getRankDownloading(int start, int end) {
        List<Long> rankList = new ArrayList<>();
        Set<Object> rankSet = zSetOperations.range(RedisConst.RANK_DOWNLOADING_PREFIX, start, end);
        if (rankSet != null && rankSet.size() > 0) {
            rankSet.forEach(tid -> {
                rankList.add((Long) tid);
            });
        }
        return rankList;
    }

    @Override
    public List<Long> getRankSeeding(int start, int end) {
        List<Long> rankList = new ArrayList<>();
        Set<Object> rankSet = zSetOperations.range(RedisConst.RANK_SEEDING_PREFIX, start, end);
        if (rankSet != null && rankSet.size() > 0) {
            rankSet.forEach(tid -> {
                rankList.add((Long) tid);
            });
        }
        return rankList;
    }
}
