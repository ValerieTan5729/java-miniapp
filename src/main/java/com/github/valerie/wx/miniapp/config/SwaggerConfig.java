package com.github.valerie.wx.miniapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket creatRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.valerie.demo"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                                 .title("Spring Boot API 文档") // 文档标题
                                 .description("SpringBoot整合swagger") // 文档描述
                                 .version("0.1") // 版本号
                                 .contact(new Contact("valerie", "www.baidu.com", "gzbn@mail")) // 联系方式
                                 .build());
    }
}
