package com.lsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:54
 */
// 开启EurekaClient 注解，目前版本如果配置了Eureka 注册中心，默认会开启该注解
//@EnableEurekaClient
@SpringBootApplication
// 开启Zuul 注解
@EnableZuulProxy
// 开启数据监控注解
@EnableHystrixDashboard
// 开启重试注解
@EnableRetry
public class ZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class,args);
    }
}
