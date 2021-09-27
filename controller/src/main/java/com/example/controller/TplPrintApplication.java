package com.example.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching  //开启缓存
// 配置 spring 扫描的包
@MapperScan(value = "com.example.dao.mapper")
@EnableTransactionManagement        //启动事务
@SpringBootApplication(scanBasePackages={"com.example"})
public class TplPrintApplication {

    public static void main(String[] args) {
        SpringApplication.run(TplPrintApplication.class, args);
    }

}
