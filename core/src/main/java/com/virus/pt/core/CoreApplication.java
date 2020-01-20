package com.virus.pt.core;

import com.virus.pt.common.util.VirusUtils;
import com.virus.pt.db.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoreApplication {
    private static final Logger logger = LoggerFactory.getLogger(CoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
        logger.info("CoreApplication is running");
    }

    @Bean
    CommandLineRunner init(ConfigService configService) {
        return (args -> {
            // 先删除再缓存
            configService.removeRedisConfig();
            VirusUtils.config = configService.getConfig();
        });
    }
}
