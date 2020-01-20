package com.virus.pt.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 3:04 下午
 * @email zzy.main@gmail.com
 */
@Configuration
@ComponentScan(basePackages = {"com.virus.pt.mail.service"})
public class MailConfig {
}
