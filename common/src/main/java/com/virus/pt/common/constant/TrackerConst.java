package com.virus.pt.common.constant;

/**
 * @author intent
 * @date 2019/7/24 11:45
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class TrackerConst {
    public static final int INTERVAL = 3600;
    public static final int MIN_INTERVAL = 60;
    public static final String USER_ANENT = "User-Agent";
    // 协议
    public static final int INFO_HASH_LENGTH = 20;
    public static final String INFO_HASH_PARAM = "info_hash";
    public static final String PEER_ID_PARAM = "peer_id";
    public static final String PORT_PARAM = "port";
    public static final String UPLOAD_PARAM = "uploaded";
    public static final String DOWNLOAD_PARAM = "downloaded";
    public static final String LEFT_PARAM = "left";
    public static final String PASSKEY_PARAM = "passkey";
    public static final int PASSKEY_LEN = 32;
    public static final String KEY_PARAM = "key";
    public static final String EVENT_PARAM = "event";
    public static final String IP_PARAM = "ip";
    public static final String IPV4_PARAM = "ipv4";
    public static final String IPV6_PARAM = "ipv6";
    // event
    public static final String STARTED = "started";
    public static final String STOP = "stopped";
    public static final String PAUSED = "paused";
    public static final String COMPLETED = "completed";
    public static final String[] TRACKER_EVENT = new String[]{STARTED, STOP, PAUSED, COMPLETED};
    // port
    public static final int MIN_PORT = 0;
    public static final int MAX_PORT = 0xffff;
}
