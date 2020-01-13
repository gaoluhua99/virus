package com.virus.pt.model.dataobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author intent
 * @date 2019/7/24 11:43
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Setter
@Getter
@ToString
public class Peer implements Serializable {
    private static final long serialVersionUID = -6801111193543281051L;

    private long userDataId;
    private String ip;
    private String ipv6;
    private Long port;
    // 上传量
    private long uploaded;
    // 上传速度
    private long uploadSpeed;
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
    private String userAgent;
    // 客服端key
    private String key;
    // 状态
    private int state;
}
