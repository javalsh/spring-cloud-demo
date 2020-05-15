package com.lsh.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/15 21:16
 */
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    
    /**
     * @Description TODO
     * @Params [exchange, chain]
     * @Return reactor.core.publisher.Mono<java.lang.Void>
     * @Author LSH
     * @Date 2020/5/15 21:18
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("自定义全局过滤器被执行.....");
        return chain.filter(exchange);
    }

    /**
     * @Description TODO
     * @Params []
     * @Return int
     * @Author LSH
     * @Date 2020/5/15 22:03
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
