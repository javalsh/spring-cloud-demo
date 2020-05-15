package com.lsh.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/15 22:03
 */
@Configuration
public class KeyResolverConfiguration {

    /**
     * @Description 限流规则
     * @Params []
     * @Return org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @Author LSH
     * @Date 2020/5/15 22:06
     */
    @Bean
    public KeyResolver pathKeyResolver() {
        /*return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getPath().toString());
            }
        };*/
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }
    /**
       * 根据参数限流
       *
       * @return
       */
    @Bean
    public KeyResolver parameterKeyResolver() {
        return exchange ->
                Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }
    /**
    * 根据IP 限流
    *
    * @return
    */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange ->
            Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}
