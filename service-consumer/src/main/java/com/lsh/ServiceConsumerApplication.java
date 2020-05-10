package com.lsh;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 17:33
 */
// 开启缓存注解
//@EnableCaching
// 开启熔断器注解2 选1
//@EnableCircuitBreaker
@EnableHystrix
// 开启数据监控注解
@EnableHystrixDashboard
@SpringBootApplication
public class ServiceConsumerApplication {


    @Bean
    public RandomRule randomRule(){
        return new RandomRule();
    }
    @Bean
    @LoadBalanced // 负载均衡注解
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApplication.class,args);
    }


}
