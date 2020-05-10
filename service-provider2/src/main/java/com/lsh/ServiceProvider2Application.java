package com.lsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:54
 */
// 开启EurekaClient 注解，目前版本如果配置了Eureka 注册中心，默认会开启该注解
//@EnableEurekaClient
@SpringBootApplication
public class ServiceProvider2Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider2Application.class,args);
    }
}
