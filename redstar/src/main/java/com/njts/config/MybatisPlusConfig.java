package com.njts.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;


import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement //开启事务注解
@Configuration // 配置类
public class MybatisPlusConfig {

   // 注册乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }



    //分页拦截器
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return  new PaginationInterceptor();
    }

    //性能分析
//    @Bean
//    @Profile({"dev","test"})
//    public PerformanceInterceptor performanceInterceptor(){
//       PerformanceInterceptor performanceInterceptor=new PerformanceInterceptor();
//       performanceInterceptor.setMaxTime(50);
//       performanceInterceptor.setFormat(true);
//       return performanceInterceptor;
//    }


}
