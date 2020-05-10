package com.lsh;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 17:33
 */
// 开启熔断器注解2 选1
//@EnableCircuitBreaker
@EnableHystrix
// 开启数据监控注解
@EnableHystrixDashboard
// 开启FeignClients 注解
@EnableFeignClients
@SpringBootApplication
public class ServiceConsumerFeignApplication {

    /*
       NONE：不记录任何信息，默认值
       BASIC：记录请求方法、请求URL、状态码和用时
       HEADERS：在BASIC 基础上再记录一些常用信息
       FULL：记录请求和相应的所有信息
    */

    @Bean
    public Logger.Level getLog() {
        return Logger.Level.FULL;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerFeignApplication.class,args);
    }


}
