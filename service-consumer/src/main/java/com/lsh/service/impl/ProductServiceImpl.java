package com.lsh.service.impl;

import com.lsh.pojo.Product;
import com.lsh.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/8 17:07
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;
//    //请求缓存实例start-------------------------------------------------------------------------------------------------
//    @Override
//    @Cacheable(cacheNames = "orderService:product:list")
//    public List<Product> selectProductList() {
//        return restTemplate.exchange(
//                "http://service-provider/product/list",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Product>>() {
//                }
//        ).getBody();
//    }
//    @Override
//    //缓存注释
//    @Cacheable(cacheNames = "orderService:product:single", key = "#id")
//    public Product selectProductById(Integer id) {
//        return restTemplate.getForObject("http://service-provider/product/" + id, Product.class);
//
//    }
//    //请求缓存实例end---------------------------------------------------------------------------------------------------


//    //线程池隔离实例start-------------------------------------------------------------------------------------------------
//    @HystrixCommand(groupKey = "order-productService-listPool",// 服务名称，相同名称使用同一个线程池
//        commandKey = "selectProductList",// 接口名称，默认为方法名
//        threadPoolKey = "order-productService-listPool",// 线程池名称，相同名称使用同一个线程池
//        commandProperties = {
//                // 超时时间，默认1000ms
//                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//        },
//        threadPoolProperties = {
//             // 线程池大小
//             @HystrixProperty(name = "coreSize", value = "6"),
//             // 队列等待阈值(最大队列长度，默认-1)
//             @HystrixProperty(name = "maxQueueSize", value = "100"),
//             // 线程存活时间，默认1min
//             @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//             // 超出队列等待阈值执行拒绝策略
//             @HystrixProperty(name = "queueSizeRejectionThreshold", value = "100")
//        }, fallbackMethod = "selectProductListFallback"
//    )
//    @Override
//    public List<Product> selectProductList() {
//        System.out.println(Thread.currentThread().getName() + "-----selectProductList------");
//        return restTemplate.exchange(
//                "http://service-provider/product/list",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Product>>() {
//                }
//        ).getBody();
//    }
//    // 托底数据
//    private List<Product> selectProductListFallback() {
//        System.out.println("-----selectProductListFallback-----");
//        return Arrays.asList(
//                new Product(1, "托底数据-华为手机", 2, 5888D),
//                new Product(2, "托底数据-联想笔记本", 1, 6888D),
//                new Product(3, "托底数据-小米平板", 5, 2666D)
//        );
//    }
//    // 线程池隔离
//    @HystrixCommand(groupKey = "order-productService-singlePool",// 服务名称，相同名称使用同一个线程池
//        commandKey = "selectProductById",// 接口名称，默认为方法名
//        threadPoolKey = "order-productService-singlePool",// 线程池名称，相同名称使用同一个线程池
//        commandProperties = {
//            // 超时时间，默认1000ms
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//        },
//        threadPoolProperties = {
//            // 线程池大小
//            @HystrixProperty(name = "coreSize", value = "3"),
//            // 队列等待阈值(最大队列长度，默认-1)
//            @HystrixProperty(name = "maxQueueSize", value = "100"),
//            // 线程存活时间，默认1min
//            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//            // 超出队列等待阈值执行拒绝策略
//            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "100")
//        }
//    )
//    @Override
//    public Product selectProductById(Integer id) {
//        System.out.println(Thread.currentThread().getName()+"-----selectProductById------");
//        return restTemplate.getForObject("http://service-provider/product/" + id, Product.class);
//
//    }
//    //线程池隔离实例end---------------------------------------------------------------------------------------------------

    //信号量隔离实例start-------------------------------------------------------------------------------------------------
    @HystrixCommand(commandProperties = {
        // 超时时间，默认1000ms
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
        // 信号量隔离
        @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"),
        // 信号量最大并发，调小一些方便模拟高并发
        @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "6")
    }, fallbackMethod = "selectProductListFallback")
    @Override
    public List<Product> selectProductList() {
        System.out.println(Thread.currentThread().getName() + "-----selectProductList------");
        return restTemplate.exchange(
                "http://service-provider/product/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }
        ).getBody();
    }
    // 托底数据
    private List<Product> selectProductListFallback() {
        System.out.println("-----selectProductListFallback-----");
        return Arrays.asList(
                new Product(1, "托底数据-华为手机", 2, 5888D),
                new Product(2, "托底数据-联想笔记本", 1, 6888D),
                new Product(3, "托底数据-小米平板", 5, 2666D)
        );
    }

    //信号量隔离实例end---------------------------------------------------------------------------------------------------



    //服务熔断实例start-----------------------------------------------------------------------------------------------------
    @HystrixCommand(commandProperties = {
        // 10s 内请求数大于20 个就启动熔断器，当请求符合熔断条件触发fallbackMethod默认20 个
        @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
        // 请求错误率大于50% 就启动熔断器，然后for 循环发起重试请求，当请求符合熔断条件触发fallbackMethod
        @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
        // 熔断多少秒后去重试请求，默认5s
        @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000"),
        }, fallbackMethod = "selectProductByIdFallback"
    )
    @Override
    public Product selectProductById(Integer id) {
        System.out.println(Thread.currentThread().getName()+"-----selectProductById------");
        System.out.println("-----selectProductById-----"
                       + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

        // 模拟查询主键为1 的商品信息会导致异常
        if (1 == id)
            throw new RuntimeException("查询主键为1 的商品信息导致异常");

        return restTemplate.getForObject("http://service-provider/product/" + id, Product.class);

    }
    // 托底数据
    private Product selectProductByIdFallback(Integer id) {
        return new Product(id, "托底数据", 2, 6666D);
    }

    //服务熔断实例end-----------------------------------------------------------------------------------------------------



    //请求合并实例start-----------------------------------------------------------------------------------------------------
    // 处理请求合并的方法一定要支持异步，返回值必须是Future<T>
    @HystrixCollapser(batchMethod = "selectProductListByIds", // 合并请求方法
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL, // 请求方式
            collapserProperties = {
                    // 间隔多久的请求会进行合并，默认10ms
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "20"),
                    // 批处理之前，批处理中允许的最大请求数
                    @HystrixProperty(name = "maxRequestsInBatch", value = "200")
            })
    @Override
    public Future<Product> selectFutureProductById(Integer id) {
        System.out.println("-----orderService-----selectProductById-----");
        return null;
    }

    // 声明需要服务容错的方法
    @HystrixCommand
    @Override
    public List<Product> selectProductListByIds(List<Integer> ids) {
        System.out.println("----------ProductService-----selectProductListByIds-------");
        StringBuffer stringBuffer = new StringBuffer();
        ids.forEach(id -> stringBuffer.append("id="+id+"&"));
        return restTemplate.getForObject("http://service-provider/product/listByIds?"+stringBuffer.toString(),List.class);
    }
    //请求合并实例end-----------------------------------------------------------------------------------------------------


}
