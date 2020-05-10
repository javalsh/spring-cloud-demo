package com.lsh.fallback;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/14 22:31
 */
public class ConsumerBlockFallbackProvider implements ZuulBlockFallbackProvider {
    private Logger logger =
            LoggerFactory.getLogger(ConsumerBlockFallbackProvider.class);
    @Override
    public String getRoute() {
        return "service-consumer"; // 服务名称
    }
    @Override
    public BlockResponse fallbackResponse(String route, Throwable cause) {
        logger.error("{} 服务触发限流", route);
        if (cause instanceof BlockException) {
            return new BlockResponse(429, "服务访问压力过大，请稍后再试。", route);
        } else {
            return new BlockResponse(500, "系统错误，请联系管理员。", route);
        }
    }

}
