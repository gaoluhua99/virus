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
    DOWNLOAD(0, "下载"),
    UPLOAD(1, "上传");

    private int code;
    private String status;

    PeerStateEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
