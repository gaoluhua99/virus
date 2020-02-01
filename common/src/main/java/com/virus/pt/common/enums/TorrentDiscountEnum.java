package com.virus.pt.common.enums;

import com.virus.pt.model.dataobject.TorrentDiscount;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/1 11:21 上午
 * @email zzy.main@gmail.com
 */
@Getter
@ToString
public enum TorrentDiscountEnum {
    NORMAL(1, 1F, 1F, 1F, 0L, "1倍上传/1倍下载/1倍做种");

    private long id;
    private float upload;
    private float download;
    private float seed;
    private long limitTime;
    private String description;

    TorrentDiscountEnum(int id, float upload, float download, float seed, long limitTime, String description) {
        this.id = id;
        this.upload = upload;
        this.download = download;
        this.seed = seed;
        this.limitTime = limitTime;
        this.description = description;
    }

    public static TorrentDiscount toDiscount(TorrentDiscountEnum discountEnum) {
        TorrentDiscount discount = new TorrentDiscount();
        discount.setId(discountEnum.getId());
        discount.setUploading(BigDecimal.valueOf(discountEnum.getUpload()));
        discount.setDownloading(BigDecimal.valueOf(discountEnum.getDownload()));
        discount.setSeeding(BigDecimal.valueOf(discountEnum.getSeed()));
        discount.setRemark(discountEnum.getDescription());
        return discount;
    }
}
