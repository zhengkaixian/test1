package com.zero.base.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

@Configuration
@MapperScan("com.zero.**.mapper")
public class MybatisPlusConfig {
	 static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/***
     * plus 的性能优化
     * 开发环境使用
     * @return
     */
    @Bean
    @Profile("dev")
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
 //       <!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->
        performanceInterceptor.setMaxTime(60000);
 //       <!--SQL是否格式化 默认false-->
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
   
}
