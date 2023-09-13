package com.njts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableCaching
@SpringBootApplication
@MapperScan(basePackages = "com.njts.mapper")
public class RedstarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedstarApplication.class, args);
    }

}
