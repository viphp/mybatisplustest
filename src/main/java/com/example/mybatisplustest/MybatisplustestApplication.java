package com.example.mybatisplustest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.mybatisplustest.dao")
@SpringBootApplication
public class MybatisplustestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplustestApplication.class, args);
    }

}
