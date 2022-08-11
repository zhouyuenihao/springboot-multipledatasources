package com.mzd.multipledatasources.config;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouyue01
 * @date 2021/8/31
 */
@Configuration
public class MybatisInterceptor {

    @Bean
    public Interceptor logInterceptor() {
        LogInterceptor logInterceptor = new LogInterceptor();
        return logInterceptor;
    }
}
