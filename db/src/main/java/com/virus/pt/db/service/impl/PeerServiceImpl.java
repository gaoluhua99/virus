package com.virus.pt.db.service.impl;

import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.db.service.PeerService;
import com.virus.pt.model.dataobject.Peer;
import com.virus.pt.model.dto.TrackerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    private ValueOperations<String, Object> valueOperations;

    @Override
    public void savePeer(long tid, Peer peer) {
        valueOperations.set(RedisConst.PEER_PREFIX + tid +
                        RedisConst.REDIS_REGEX + peer.getIp() +
                        RedisConst.REDIS_REGEX + peer.getPort() +
                        RedisConst.REDIS_REGEX + peer.getPeerId(),
                peer, Duration.ofSeconds(TrackerResponse.INTERVAL));
    }

    @Override
    public void saveAllPeer(long tid, List<Peer> peerList) {
        peerList.forEach(peer -> {
            savePeer(tid, peer);
        });
    }

    @Override
    public Peer getPeer(long tid, Peer peer) {
        return (Peer) valueOperations.get(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId());
    }

    @Override
    public List<Peer> getPeerList(long tid) {
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
    public void removePeer(long tid, Peer peer) {
        redisTemplate.delete(RedisConst.PEER_PREFIX + tid +
                RedisConst.REDIS_REGEX + peer.getIp() +
                RedisConst.REDIS_REGEX + peer.getPort() +
                RedisConst.REDIS_REGEX + peer.getPeerId());
    }

    @Override
    public long getCount(long tid) {
        Set<String> keys = redisTemplate.keys(RedisConst.PEER_PREFIX + tid + RedisConst.REDIS_ALL_KEY);
        if (keys != null && keys.size() > 0) {
            return keys.size();
        }
        return 0;
    }
}
