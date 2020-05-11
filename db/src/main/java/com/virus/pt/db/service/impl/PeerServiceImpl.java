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

    /**
     * 存储peer
     *
     * @param tid  种子id
     * @param peer peer
     */
    @Override
    public void save(long tid, Peer peer) {
        valueOperations.set(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId() +
                RedisConst.REDIS_REGEX + peer.getState(), peer, Duration.ofSeconds(RedisConst.PEER_EXP));
        if (peer.getState() == PeerStateEnum.SEEDING.getCode()) {
            zSetOperations.incrementScore(RedisConst.RANK_SEEDING_PREFIX, tid, 1);
        } else if (peer.getState() == PeerStateEnum.DOWNLOADING.getCode()) {
            zSetOperations.incrementScore(RedisConst.RANK_DOWNLOADING_PREFIX, tid, 1);
        }
    }

    /**
     * 存储所有peerList
     *
     * @param tid      种子id
     * @param peerList peerList
     */
    @Override
    public void saveAll(long tid, List<Peer> peerList) {
        peerList.forEach(peer -> {
            save(tid, peer);
        });
    }

    /**
     * 获取peer
     *
     * @param tid  种子id
     * @param peer peer的一些基本信息
     * @return peer
     */
    @Override
    public Peer get(long tid, Peer peer) {
        Set<String> keys = redisTemplate.keys(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId() + RedisConst.REDIS_ALL_KEY +
                RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() == 1) {
            String key = keys.iterator().next();
            Peer retPeer = (Peer) valueOperations.get(key);
            if (retPeer != null) {
                // 根据key获取下载或者做种
                int state = getPeerStateByKey(key);
                // 手动设置peer状态
                if (state == PeerStateEnum.SEEDING.getCode()) {
                    retPeer.setState(PeerStateEnum.SEEDING.getCode());
                } else if (state == PeerStateEnum.DOWNLOADING.getCode()) {
                    retPeer.setState(PeerStateEnum.DOWNLOADING.getCode());
                }
                return retPeer;
            }
        }
        return null;
    }

    /**
     * 根据key获取下载或者做种
     *
     * @param key key
     * @return 状态值
     */
    private int getPeerStateByKey(String key) {
        return Integer.parseInt(key.substring(key.length() - 1));
    }

    /**
     * 根据种子id获取所有peer
     *
     * @param tid 种子id
     * @return peer集合
     */
    @Override
    public List<Peer> getList(long tid) {
        List<Peer> peerList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisConst.PEER_PREFIX + tid + RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Peer peer = (Peer) valueOperations.get(key);
                if (peer != null) {
                    // 根据key获取下载或者做种
                    int state = getPeerStateByKey(key);
                    if (state == PeerStateEnum.DOWNLOADING.getCode()) {
                        peer.setState(PeerStateEnum.DOWNLOADING.getCode());
                    } else if (state == PeerStateEnum.SEEDING.getCode()) {
                        peer.setState(PeerStateEnum.SEEDING.getCode());
                    }
                    peerList.add(peer);
                }
            }
        }
        return peerList;
    }

    @Override
    public void remove(long tid, Peer peer) {
        String key = RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId()
                + RedisConst.REDIS_REGEX;
        if (peer.getState() == PeerStateEnum.SEEDING.getCode()) {
            zSetOperations.incrementScore(RedisConst.RANK_SEEDING_PREFIX, tid, -1);
            key += PeerStateEnum.SEEDING.getCode();
        } else if (peer.getState() == PeerStateEnum.DOWNLOADING.getCode()) {
            zSetOperations.incrementScore(RedisConst.RANK_DOWNLOADING_PREFIX, tid, -1);
            key += PeerStateEnum.DOWNLOADING.getCode();
        }
        redisTemplate.delete(key);
    }

    /**
     * 根据种子id获取peer数量
     *
     * @param tid 种子id
     * @return peer数量
     */
    @Override
    public long getCount(long tid) {
        // 通过前缀加种子id的方法获取总数
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

    /**
     * 统计种子的做种和下载情况
     *
     * @param tid 种子id
     */
    @Override
    public void statistics(long tid) {
        Set<String> keys = redisTemplate.keys(RedisConst.PEER_PREFIX + tid + RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() > 0) {
            int seedCount = 0;
            int downloadCount = 0;
            for (String key : keys) {
                int state = getPeerStateByKey(key);
//                logger.info("key: {}, state: {}", key, state);
                if (state == PeerStateEnum.SEEDING.getCode()) {
                    seedCount++;
                } else if (state == PeerStateEnum.DOWNLOADING.getCode()) {
                    downloadCount++;
                }
            }
            zSetOperations.add(RedisConst.RANK_SEEDING_PREFIX, tid, seedCount);
            zSetOperations.add(RedisConst.RANK_DOWNLOADING_PREFIX, tid, downloadCount);
        }
    }
}
