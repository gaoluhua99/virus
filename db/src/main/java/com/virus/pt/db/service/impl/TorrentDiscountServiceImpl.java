package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.TorrentDiscountDao;
import com.virus.pt.db.service.TorrentDiscountService;
import com.virus.pt.model.dataobject.TorrentDiscount;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/1 11:15 上午
 * @email zzy.main@gmail.com
 */
@Service
public class TorrentDiscountServiceImpl extends ServiceImpl<TorrentDiscountDao, TorrentDiscount>
        implements TorrentDiscountService {
    @Override
    public TorrentDiscount get(long id) {
        return getOne(new QueryWrapper<TorrentDiscount>()
                .eq("id", id)
                .eq("is_delete", false));
    }
}
