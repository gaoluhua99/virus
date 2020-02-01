package com.virus.pt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 7:36 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class TorrentDto {
    private Long id;
    private Long userAuthId;
    private String fileName;
    private Long fileSize;
    private String hash;

    private Long torrentSize;
    private Long torrentCount;

    private Long create;
    private Long modify;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TorrentDiscountDto discount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PeerDto peer;
}
