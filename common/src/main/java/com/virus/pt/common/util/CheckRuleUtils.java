package com.virus.pt.common.util;

import com.virus.pt.common.enums.TrackerResponseEnum;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;

import static com.virus.pt.common.constant.TrackerConst.*;


/**
 * @author intent
 * @date 2019/7/24 11:20
 * @about <link href=http://zzyitj.xyz//>
 */
public class CheckRuleUtils {

    private static final int[] TRACKER_PORT_BLACK_LIST = new int[]{
            22,  // SSH Port
            53,  // DNS queries
            80, 81, 8080, 8081,  // Hyper Text Transfer Protocol (HTTP) - port used for web traffic
            411, 412, 413,  //     Direct Connect Hub (unofficial)
            443,  // HTTPS / SSL - encrypted web traffic, also used for VPN tunnels over HTTPS.
            1214,  // Kazaa - peer-to-peer file sharing, some known vulnerabilities, and at least one worm (Benjamin) targeting it.
            3389,  // IANA registered for Microsoft WBT Server, used for Windows Remote Desktop and Remote Assistance connections
            4662,  // eDonkey 2000 P2P file sharing service. http://www.edonkey2000.com/
            6346, 6347,  // Gnutella (FrostWire, Limewire, Shareaza, etc.), BearShare file sharing app
            6699,  // Port used by p2p software, such as WinMX, Napster.
            6881, 6882, 6883, 6884, 6885, 6886, 6887, // BitTorrent part of full range of ports used most often (unofficial)};
    };
    private static final String[] CLIENT_WHITE_LIST = new String[]{
            "Transmission", "qBittorrent"
    };

    /**
     * 检查规则
     * userAnent, info_hash, peer_id, port, uploaded, downloaded, left, passkey 为必须参数
     *
     * @return TrackerResponseEnum
     */
    public static TrackerResponseEnum checkRule(String userAnent, String infoHash, String password, int passkeyLen,
                                                String peerId, String port,
                                                String upload, String download, String left, String event,
                                                String key) {
        // 检查请求头header
        TrackerResponseEnum trackerResponseEnum = checkHeader(userAnent);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检测infohash
        trackerResponseEnum = checkHash(infoHash);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检测passkey长度
        trackerResponseEnum = checkPasskey(password, passkeyLen);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检查peerid长度
        trackerResponseEnum = checkPeerId(peerId);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检查端口
        trackerResponseEnum = checkPort(port);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检查上传下载是否正确
        trackerResponseEnum = checkPositiveNumber(upload, download, left);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检查事件
        trackerResponseEnum = checkEvent(event);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        // 检查key
        trackerResponseEnum = checkKey(key);
        if (trackerResponseEnum != TrackerResponseEnum.SUCCESS) {
            return trackerResponseEnum;
        }
        return trackerResponseEnum;
    }

    private static TrackerResponseEnum checkKey(String key) {
        if (StringUtils.isBlank(key) || key.length() != 8) {
            return TrackerResponseEnum.KEY_ERROR;
        }
        return TrackerResponseEnum.SUCCESS;
    }

    private static TrackerResponseEnum checkHeader(String userAgent) {
        if (StringUtils.isNotBlank(userAgent)) {
            // 检查userAgent是否在白名单内
            for (String client : CLIENT_WHITE_LIST) {
                if (userAgent.contains(client)) {
                    return TrackerResponseEnum.SUCCESS;
                }
            }
        }
        return TrackerResponseEnum.HEADER_ERROR;
    }

    public static TrackerResponseEnum checkHash(String infoHash) {
        return checkHash(new String[]{infoHash});
    }

    /**
     * 检查hash是否符合规则
     *
     * @param infoHashArray infoHash数组
     * @return TrackerResponseEnum true 符合
     */
    private static TrackerResponseEnum checkHash(String[] infoHashArray) {
        for (String hash : infoHashArray) {
            if (StringUtils.isBlank(hash)) {
                return TrackerResponseEnum.INFO_HASH_EMPTY;
            }
            try {
                byte[] hashByte = URLCodec.decodeUrl(hash.getBytes());
                if (hashByte.length != INFO_HASH_LENGTH) {
                    return TrackerResponseEnum.INFO_HASH_LEN_ERROR;
                }
            } catch (DecoderException e) {
                return TrackerResponseEnum.ERROR;
            }
        }
        return TrackerResponseEnum.SUCCESS;
    }

    private static TrackerResponseEnum checkPasskey(String passkey, int len) {
        if (StringUtils.isBlank(passkey)) {
            return TrackerResponseEnum.PASSKEY_EMPTY;
        } else if (passkey.length() != len) {
            return TrackerResponseEnum.PASSKEY_LEN_ERROR;
        }
        return TrackerResponseEnum.SUCCESS;
    }


    /**
     * 检查event是否符合规则
     *
     * @param event 事件
     * @return TrackerResponseEnum
     */
    private static TrackerResponseEnum checkEvent(String event) {
        if (StringUtils.isBlank(event)) {
            return TrackerResponseEnum.SUCCESS;
        }
        for (String s : TRACKER_EVENT) {
            if (s.equals(event)) {
                return TrackerResponseEnum.SUCCESS;
            }
        }
        return TrackerResponseEnum.EVENT_ERROR;
    }

    /**
     * 检查peerId是否符合规则
     *
     * @param peerId peerId
     * @return TrackerResponseEnum
     */
    private static TrackerResponseEnum checkPeerId(String peerId) {
        if (StringUtils.isBlank(peerId)) {
            return TrackerResponseEnum.PEER_ID_EMPTY;
        }
        try {
            byte[] peerIdByte = URLCodec.decodeUrl(peerId.getBytes());
            return (peerIdByte.length == INFO_HASH_LENGTH) ? TrackerResponseEnum.SUCCESS : TrackerResponseEnum.PEER_ID_LEN_ERROR;
        } catch (DecoderException e) {
            return TrackerResponseEnum.ERROR;
        }
    }

    /**
     * 检查端口是否正确
     *
     * @param port 端口
     * @return TrackerResponseEnum
     */
    private static TrackerResponseEnum checkPort(String port) {
        if (StringUtils.isBlank(port)) {
            return TrackerResponseEnum.PORT_EMPTY;
        }
        try {
            int p = Integer.parseInt(port);
            return checkPort(p);
        } catch (NumberFormatException e) {
            return TrackerResponseEnum.ERROR;
        }
    }

    private static TrackerResponseEnum checkPort(int port) {
        // 检查是否是黑名单中
        for (int p : TRACKER_PORT_BLACK_LIST) {
            if (p == port) {
                return TrackerResponseEnum.PORT_ERROR;
            }
        }
        return (port >= MIN_PORT && port <= MAX_PORT) ? TrackerResponseEnum.SUCCESS : TrackerResponseEnum.PORT_ERROR;
    }


    /**
     * 检查是否为正数
     *
     * @param value 值
     * @return TrackerResponseEnum
     */
    private static TrackerResponseEnum checkPositiveNumber(String... value) {
        for (String s : value) {
            if (!checkPositiveNumber(s)) {
                return TrackerResponseEnum.ERROR;
            }
        }
        return TrackerResponseEnum.SUCCESS;
    }

    private static boolean checkPositiveNumber(String value) {
        if (StringUtils.isBlank(value) || value.length() > 20) {
            return false;
        }
        try {
            long v = Long.parseLong(value);
            return checkPositiveNumber(v);
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private static boolean checkPositiveNumber(long value) {
        return value >= 0;
    }
}
