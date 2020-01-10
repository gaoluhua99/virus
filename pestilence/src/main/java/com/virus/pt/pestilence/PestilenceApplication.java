package com.virus.pt.pestilence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PestilenceApplication {
    private static final Logger logger = LoggerFactory.getLogger(PestilenceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PestilenceApplication.class, args);
        logger.info("PestilenceApplication is running");
    }
}
