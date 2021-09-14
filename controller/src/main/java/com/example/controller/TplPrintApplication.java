package com.example.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.oas.annotations.EnableOpenApi;


// 配置 spring 扫描的包
@MapperScan(value = "com.example.dao.mapper")
@SpringBootApplication(scanBasePackages={"com.example"})
public class TplPrintApplication {

    public static void main(String[] args) {
        SpringApplication.run(TplPrintApplication.class, args);
    }

}
