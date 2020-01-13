package com.virus.pt.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author intent
 * @date 2019/7/14 16:52
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@ToString
public enum UserDataEnum {
    ACTIVE(1, "正常"),
    WARNING(2, "账号被警告"),
    BAN(3, "账号被禁止");

    private int code;
    private String status;

    UserDataEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
