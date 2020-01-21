package com.virus.pt.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virus.pt.db.dao.NoticeDao;
import com.virus.pt.db.service.NoticeService;
import com.virus.pt.model.dataobject.Notice;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 12:11 下午
 * @email zzy.main@gmail.com
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {
    @Override
    public IPage<Notice> getAll(long pageIndex, long pageSize) {
        return baseMapper.selectAll(new Page<Notice>(pageIndex, pageSize));
    }
}
