package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.SeriesDao;
import com.virus.pt.db.service.SeriesService;
import com.virus.pt.model.dataobject.Series;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:20 下午
 * @email zzy.main@gmail.com
 */
@Service
public class SeriesServiceImpl extends ServiceImpl<SeriesDao, Series> implements SeriesService {
    @Override
    public boolean saveRollback(Series series) {
        return save(series);
    }
}
