package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.TorrentDiscount;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/1 11:15 上午
 * @email zzy.main@gmail.com
 */
public interface TorrentDiscountService extends IService<TorrentDiscount> {
    TorrentDiscount get(long id);
}
