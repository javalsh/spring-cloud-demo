package com.lsh.ratelimit;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/14 21:42
 */
@Component
public class RateLimitKeyGenerator extends DefaultRateLimitKeyGenerator {
    public RateLimitKeyGenerator(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        super(properties, rateLimitUtils);
    }

    /**
     * @Description 限流逻辑
     * @Params [request, route, policy]
     * @Return java.lang.String
     * @Author LSH
     * @Date 2020/5/14 21:44
     */
    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        return super.key(request, route, policy)+ ":" + request.getParameter("token");

    }
}
