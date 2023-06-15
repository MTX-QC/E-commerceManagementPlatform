package com.mtx.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.TimeZone;

@SpringBootApplication
@MapperScan(basePackages = "com.mtx.mall.model.dao")
@EnableSwagger2
@EnableCaching
public class MtxMallApplication {

    public static void main(String[] args) {
        System.out.println(TimeZone.getDefault());
        SpringApplication.run(MtxMallApplication.class, args);
    }

}
