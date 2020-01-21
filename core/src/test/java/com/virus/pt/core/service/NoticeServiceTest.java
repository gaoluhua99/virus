package com.virus.pt.core.service;

import com.virus.pt.core.CoreApplicationTest;
import com.virus.pt.db.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 1:16 下午
 * @email zzy.main@gmail.com
 */
class NoticeServiceTest extends CoreApplicationTest {
    @Autowired
    private NoticeService noticeService;

    @Test
    void getAll() {
        noticeService.getAll(0, 3).getRecords().forEach(System.out::println);
    }
}