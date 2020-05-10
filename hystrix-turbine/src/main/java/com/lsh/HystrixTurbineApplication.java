package com.lsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/9 22:45
 */
// 开启熔断器注解2 选1
//@EnableCircuitBreaker
@EnableHystrix
// 开启数据监控注解
@EnableHystrixDashboard
// 开启聚合监控注解
@EnableTurbine
@SpringBootApplication
public class HystrixTurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbineApplication.class,args);
    }
}
