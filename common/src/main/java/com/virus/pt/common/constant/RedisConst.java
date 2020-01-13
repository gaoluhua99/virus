package com.virus.pt.common.constant;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:19 下午
 * @email zzy.main@gmail.com
 */
public class RedisConst {
    public static final String REDIS_ALL_KEY = "*";
    public static final String REDIS_REGEX = ":";
    public static final String PEER_PREFIX = "peer:";
    // 种子前缀
    public static final String TORRENT_PREFIX = "torrent:";
    // 种子过期时间，24小时
    public static final int TORRENT_EXP = 604800;
}
