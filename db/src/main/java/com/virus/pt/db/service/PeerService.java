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

    /**
     * 存储peer
     *
     * @param tid  种子id
     * @param peer peer
     */
    void save(long tid, Peer peer);

    /**
     * 存储所有peerList
     *
     * @param tid      种子id
     * @param peerList peerList
     */
    void saveAll(long tid, List<Peer> peerList);

    /**
     * 获取peer
     *
     * @param tid  种子id
     * @param peer peer的一些基本信息
     * @return peer
     */
    Peer get(long tid, Peer peer);

    /**
     * 根据种子id获取所有peer
     *
     * @param tid 种子id
     * @return peer集合
     */
    List<Peer> getList(long tid);

    void remove(long tid, Peer peer);

    /**
     * 根据种子id获取peer数量
     *
     * @param tid 种子id
     * @return peer数量
     */
    long getCount(long tid);

    long getDownloadingCount(long tid);

    long getSeedingCount(long tid);

    // 获取按下载人数排序好的tid集合
    List<Long> getRankDownloading(int start, int end);

    // 获取按做种人数排序好的tid集合
    List<Long> getRankSeeding(int start, int end);

    /**
     * 统计种子的做种和下载情况
     *
     * @param tid 种子id
     */
    void statistics(long tid);
}
