package com.virus.pt.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @Author: intent
 * @Date: 19-11-2 下午2:51
 */
@Getter
@ToString
public enum PeerStateEnum {
    DOWNLOADING(0, "下载中"),
    SEEDING(1, "做种中");

    private int code;
    private String status;

    PeerStateEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
