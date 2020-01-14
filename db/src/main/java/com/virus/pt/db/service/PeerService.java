package com.virus.pt.db.service;

import com.virus.pt.model.dataobject.Peer;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 4:37 下午
 * @email zzy.main@gmail.com
 */
public interface PeerService {

    void savePeer(long tid, Peer peer);

    void saveAllPeer(long tid, List<Peer> peerList);

    Peer getPeer(long tid, Peer peer);

    List<Peer> getPeerList(long tid);

    void removePeer(long tid, Peer peer);

    long getCount(long tid);

    long getDownloadingCount(long tid);

    long getSeedingCount(long tid);

    // 获取按下载人数排序好的tid集合
    List<Long> getRankDownloading(int start, int end);

    // 获取按做种人数排序好的tid集合
    List<Long> getRankSeeding(int start, int end);
}
