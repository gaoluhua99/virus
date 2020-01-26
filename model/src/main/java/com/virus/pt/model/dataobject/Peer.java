package com.virus.pt.model.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * @author intent
 * @date 2019/7/24 11:43
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Data
public class Peer implements Serializable {
    private static final long serialVersionUID = -6801111193543281051L;

    private String passkey;
    private String ip;
    private String ipv6;
    private long port;
    // 上传量
    private long uploaded;
    // 下载量
    private long downloaded;
    // 剩余
    private long left;
    // 连接时间
    private long connectTime;
    // 上一次连接时间
    private long lastConnectTime;
    // 客服端标志
    private String peerId;
    // 客服端key
    private String key;
    // 状态
    private int state;
}
