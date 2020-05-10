package com.lsh.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description 异常过滤器
 * @Author LSH
 * @Date 2020/5/14 20:13
 */
@Component
public class ErrorFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        ZuulException exception = this.findZuulException(currentContext.getThrowable());

        Throwable throwable = currentContext.getThrowable();
        logger.error("ErrorFilter..." + throwable.getCause().getMessage(),throwable);
        HttpStatus httpStatus = null;
        if (429 == exception.nStatusCode)
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        if (500 == exception.nStatusCode)
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        // 响应状态码
        currentContext.setResponseStatusCode(httpStatus.value());

        // 响应类型
        currentContext.getResponse().setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = currentContext.getResponse().getWriter();
            // 响应内容
            writer.print("{\"message\":\"" +httpStatus.getReasonPhrase()  + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
        return null;
    }

    private ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException)
            return (ZuulException) throwable.getCause().getCause();
        if (throwable.getCause() instanceof ZuulException)
            return (ZuulException) throwable.getCause();
        if (throwable instanceof ZuulException)
            return (ZuulException) throwable;
        return new ZuulException(throwable,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    }

    }
