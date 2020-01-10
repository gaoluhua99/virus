package com.virus.pt.pestilence.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author intent
 * @date 2019/8/3 11:21
 * @about <link href='http://zzyitj.xyz/'/>
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value = "com.virus.pt.db.dao")
@ComponentScan(basePackages = {"com.virus.pt.db.service"})
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        paginationInterceptor.setLimit(50);
        return paginationInterceptor;
    }
}