package com.virus.pt.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 8:22 下午
 * @email zzy.main@gmail.com
 */
@Getter
@ToString
public enum PostInfoTypeEnum {
    DOUBAN_MOVIE(1, "豆瓣电影"),
    DOUBAN_MUSIC(2, "豆瓣音乐"),
    DOUBAN_BOOK(3, "豆瓣图书"),
    IMDB(10, "IMDB");

    private int infoType;
    private String name;

    PostInfoTypeEnum(int infoType, String name) {
        this.infoType = infoType;
        this.name = name;
    }
}
