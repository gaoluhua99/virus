package com.virus.pt.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 7:37 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class TorrentDiscountDto {
    private BigDecimal uploading;
    private BigDecimal downloading;
    private BigDecimal seeding;
}
