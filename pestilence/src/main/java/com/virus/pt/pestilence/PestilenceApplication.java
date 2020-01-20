package com.virus.pt.pestilence;

import com.virus.pt.common.util.VirusUtils;
import com.virus.pt.db.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PestilenceApplication {
    private static final Logger logger = LoggerFactory.getLogger(PestilenceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PestilenceApplication.class, args);
        logger.info("PestilenceApplication is running");
    }

    @Bean
    CommandLineRunner init(ConfigService configService) {
        return (args -> {
            // 先删除再缓存
            configService.removeFromRedis();
            VirusUtils.config = configService.get();
        });
    }
}
