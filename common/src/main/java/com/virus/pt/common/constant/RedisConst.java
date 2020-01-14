package com.virus.pt.common.constant;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 3:19 下午
 * @email zzy.main@gmail.com
 */
public class RedisConst {
    // 30天
    public static final long MONTH_EXP = 18144000;
    // 7天
    public static final long SEVEN_DAT_EXP = 604800;
    // 24小时
    public static final long ONE_DAT_EXP = 86400;
    // 1小时
    public static final long ONE_HOUR_EXP = 3600;
    // redis config
    public static final String REDIS_ALL_KEY = "*";
    public static final String REDIS_REGEX = ":";
    // config
    public static final String CONFIG_PREFIX = "config";
    public static final long CONFIG_EXP = SEVEN_DAT_EXP;
    // peer
    public static final String PEER_PREFIX = "peer:";
    public static final long PEER_EXP = ONE_HOUR_EXP;
    // ranking list
    public static final String RANK_DOWNLOADING_PREFIX = "rankDownloadingZSet";
    public static final String RANK_SEEDING_PREFIX = "rankSeedingZSet";
    // torrent
    public static final String TORRENT_PREFIX = "torrent:";
    public static final long TORRENT_EXP = SEVEN_DAT_EXP;
    // user data
    public static final String USER_DATA_PREFIX = "userData:";
    public static final long USER_DATA_EXP = SEVEN_DAT_EXP;
}
