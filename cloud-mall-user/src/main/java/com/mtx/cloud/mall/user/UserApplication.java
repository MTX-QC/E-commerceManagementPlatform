package com.mtx.cloud.mall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 描述：     启动类
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.mtx.cloud.mall.user.model.dao")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
