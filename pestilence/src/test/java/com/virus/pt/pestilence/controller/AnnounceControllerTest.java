package com.virus.pt.pestilence.controller;

import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.db.service.PeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/5/11 5:55 下午
 * @email zzy.main@gmail.com
 */
@SpringBootTest
class AnnounceControllerTest {
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Test
    public void test() {
        zSetOperations.add(RedisConst.RANK_SEEDING_PREFIX, 5, 100);
    }
}