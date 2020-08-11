package com.zdxj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zdxj.mapper")
public class CmsCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsCoreApplication.class, args);
    }

}
