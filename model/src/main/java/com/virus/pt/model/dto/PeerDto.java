package com.virus.pt.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 7:39 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PeerDto {
    private long uploadCount;
    private long downloadCount;
    private long completeCount;
}
