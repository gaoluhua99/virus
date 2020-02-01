package com.virus.pt.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.Series;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:20 下午
 * @email zzy.main@gmail.com
 */
public interface SeriesService extends IService<Series> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(Series series);

    List<Series> getListByPostId(long postId);
}
