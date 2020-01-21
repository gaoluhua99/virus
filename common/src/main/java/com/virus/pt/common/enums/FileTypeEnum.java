package com.virus.pt.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author intent
 * @date 2019/7/23 19:54
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@ToString
public enum FileTypeEnum {
    IMAGE(0, "image", "图片类型"),
    CAPTCHA_IMAGE(1, "jpg", "验证码图片类型");

    private int id;
    private String name;
    private String description;

    FileTypeEnum(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
