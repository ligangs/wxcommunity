package com.gang.wxcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gang.wxcommunity.mapper")
@SpringBootApplication
public class WxcommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxcommunityApplication.class, args);
    }

}
