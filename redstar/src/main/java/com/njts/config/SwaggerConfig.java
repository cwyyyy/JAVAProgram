package com.njts.config;

import com.njts.utils.WarehouseConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2//开启
public class SwaggerConfig {
   @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //加了ApiOperation注解的类，才生成接口文档
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，才生成接口文档
            .apis(RequestHandlerSelectors.basePackage("com.njts.controller"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(security());         //主要关注点--统一填写一次token
    }

	private List<ApiKey> security() {
        return newArrayList(
            new ApiKey(WarehouseConstants.HEADER_TOKEN_NAME, "Token", "header")
        );
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            //页面标题
           .title("红星仓库平台API接口文档")
            //创建人
           .contact(new Contact("陈海龙", "http://www.javachat.cc",
                 "308752798@qq.com"))
           //版本号
          .version("1.0")
           //描述
          .description("系统API描述")
          .build();

}}
