package com.lsh.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Description 对服务做服务降级处理
 * @Author LSH
 * @Date 2020/5/14 21:02
 */
@Component
public class ServiceConsumerFallback implements FallbackProvider {
    /**
     * @Description TODO
     *  return - 返回fallback 处理哪一个服务。返回的是服务的名称。
     * 推荐- 为指定的服务定义特性化的fallback 逻辑。
     * 推荐- 提供一个处理所有服务的fallback 逻辑。
     * 好处- 某个服务发生超时，那么指定的fallback 逻辑执行。如果有新服务上线，未提供fallback 逻辑，有一个通用的。
     * @Params
     * @Return java.lang.String
     * @Author LSH
     * @Date 2020/5/14 21:04
     */
    @Override
    public String getRoute() {
        return "service-consumer";
    }

    /**
     * @Description 对服务做服务降级处理
     * @Params [route降级服务名称, cause服务异常信息]
     * @Return org.springframework.http.client.ClientHttpResponse
     * @Author LSH
     * @Date 2020/5/14 21:06
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * @Description ClientHttpResponse 的fallback 的状态码返回HttpStatus
             * @Params []
             * @Return org.springframework.http.HttpStatus
             * @Author LSH
             * @Date 2020/5/14 21:09
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;

            }
            /**
             * @Description ClientHttpResponse 的fallback 的状态码返回int
             * @Params []
             * @Return int
             * @Author LSH
             * @Date 2020/5/14 21:09
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();

            }

            /**
             * @Description ClientHttpResponse 的fallback 的状态码返回String
             * @Params []
             * @Return java.lang.String
             * @Author LSH
             * @Date 2020/5/14 21:09
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();

            }

            /**
             * @Description 回收资源方法
             * @Params []
             * @Return void
             * @Author LSH
             * @Date 2020/5/14 21:10
             */
            @Override
            public void close() {

            }

            /**
             * @Description 设置响应体, Zuul 会将本方法返回的输入流数据读取，并通过HttpServletResponse 的输出流
             * 输出到客户端。
             * @Params []
             * @Return java.io.InputStream
             * @Author LSH
             * @Date 2020/5/14 21:10
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("{\"message\":\"服务不可用，请稍后再试。\"}".getBytes());

            }

            /**
             * @Description 设置响应的头信息
             * @Params []
             * @Return org.springframework.http.HttpHeaders
             * @Author LSH
             * @Date 2020/5/14 21:10
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders header = new HttpHeaders();
                header.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
                return header;

            }
        };
    }
}
