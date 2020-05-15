package com.lsh.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Description 限流规则配置类
 * @Author LSH
 * @Date 2020/5/15 22:52
 */
@Configuration
public class GatewayConfiguration {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
        // 加载网关限流规则
        initGatewayRules();
        // 加载自定义限流异常处理器
        initBlockHandler();
        //加载限流分组
        initCustomizedApis();
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
//        rules.add(new GatewayFlowRule("service-provider")
//            .setCount(3) // 限流阈值
//            .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是1 秒
        // --------------------限流分组----------start----------
        rules.add(new GatewayFlowRule("product-api")
                .setCount(3)
                .setIntervalSec(60)
        );
        rules.add(new GatewayFlowRule("order-api")
                .setCount(3)
                .setIntervalSec(60)
        );
   // --------------------限流分组-----------end-----------

        // 加载网关限流规则
        GatewayRuleManager.loadRules(rules);
    }
    
    /**
    * 自定义限流异常处理器
    */
    private void initBlockHandler() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                Map<String, String> result = new HashMap<>();
                    result.put("code", String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
                    result.put("message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                    result.put("route", "service-provider");
                    return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result));
                }
            };
        // 加载自定义限流异常处理器
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    /**
     * @Description 限流分组
     * @Params []
     * @Return void
     * @Author LSH
     * @Date 2020/5/15 23:31
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();

        ApiDefinition api1 = new ApiDefinition("product-api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                                        //
                                       add(new ApiPathPredicateItem().setPattern("/service-provider/product/**")
                                               .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX)
                                       );
                                   }}
                );
        ApiDefinition api2 = new ApiDefinition("order-api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                                        //完全匹配
                                       add(new ApiPathPredicateItem().setPattern("/service-consumer/user/manage")
                                       );
                                   }}
                );
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }


}
