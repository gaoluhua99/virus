package com.virus.pt.core.service;

import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.vo.PostReleaseVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:35 下午
 * @email zzy.main@gmail.com
 */
public interface PostReleaseService {
    @Transactional(rollbackFor = Exception.class)
    boolean saveRollback(long userId, String qualityJson, PostReleaseVo postReleaseVo) throws TipException;
}
