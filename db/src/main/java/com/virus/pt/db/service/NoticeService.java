package com.virus.pt.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.virus.pt.model.dataobject.Notice;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 12:10 下午
 * @email zzy.main@gmail.com
 */
public interface NoticeService extends IService<Notice> {
    IPage<Notice> getAll(long pageIndex, long pageSize);
}
