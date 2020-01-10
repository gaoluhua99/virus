package com.virus.pt.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {
    private static final Logger logger = LoggerFactory.getLogger(CoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
        logger.info("CoreApplication is running");
    }
}
