package com.virus.pt.pestilence;

import com.virus.pt.common.util.JwtUtils;
import com.virus.pt.common.util.VirusUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 8:04 下午
 * @email zzy.main@gmail.com
 */
@SpringBootTest
class PestilenceApplicationTest {
    @Test
    public void test() {
        System.out.println(JwtUtils.createJWTToken(VirusUtils.config.getTokenSecret(), 1, VirusUtils.config.getTokenExp()));
    }
}