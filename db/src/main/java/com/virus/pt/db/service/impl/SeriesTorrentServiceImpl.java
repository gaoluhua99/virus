package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.SeriesTorrentDao;
import com.virus.pt.db.service.SeriesTorrentService;
import com.virus.pt.model.dataobject.SeriesTorrent;
import com.virus.pt.model.dataobject.Torrent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:23 下午
 * @email zzy.main@gmail.com
 */
@Service
public class SeriesTorrentServiceImpl extends ServiceImpl<SeriesTorrentDao, SeriesTorrent>
        implements SeriesTorrentService {
    @Override
    public boolean saveRollback(SeriesTorrent seriesTorrent) {
        return save(seriesTorrent);
    }

    @Override
    public List<Torrent> getListBySeriesId(long seriesId) {
        return baseMapper.selectTorrentListBySeriesId(seriesId);
    }
}
