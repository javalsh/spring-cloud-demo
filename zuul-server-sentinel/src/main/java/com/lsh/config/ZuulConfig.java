package com.lsh.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
import com.lsh.fallback.ConsumerBlockFallbackProvider;
import com.netflix.zuul.ZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/14 22:16
 */
@Configuration
public class ZuulConfig {
    @Bean
    public ZuulFilter sentinelZuulPreFilter() {
        // We can also provider the filter order in the constructor.
        return new SentinelZuulPreFilter();
    }

    @Bean
    public ZuulFilter sentinelZuulPostFilter() {
        return new SentinelZuulPostFilter();
    }

    @Bean
    public ZuulFilter sentinelZuulErrorFilter() {
        return new SentinelZuulErrorFilter();
    }

    // Spring 容器初始化的时候执行该方法
    @PostConstruct
    public void doInit() {
        // 注册FallbackProvider
        ZuulBlockFallbackManager.registerProvider(new ConsumerBlockFallbackProvider());

        // 加载网关限流规则
        initGatewayRules();
    }
    /**
    * 网关限流规则
    */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        /*
        resource：资源名称，可以是网关中的route 名称或者用户自定义的API 分组名称
        count：限流阈值
        intervalSec：统计时间窗口，单位是秒，默认是1 秒
        */
        rules.add(new GatewayFlowRule("service-consumer").setCount(3) // 限流阈值
                .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是1 秒
        // 加载网关限流规则
        GatewayRuleManager.loadRules(rules);
    }

}
