package com.lsh.fallback;

import com.lsh.pojo.Product;
import com.lsh.service.ProductService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/9 15:40
 */
@Service
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService> {

    // 获取日志，在需要捕获异常的方法中进行处理
    Logger logger = LoggerFactory.getLogger(ProductServiceFallbackFactory.class);




    @Override
    public ProductService create(Throwable throwable) {
        return new ProductService() {
            @Override
            public List<Product> selectProductList() {
                logger.error("product-service 服务的selectProductList 方法出现异常，异常信息如下："+ throwable);
                return Arrays.asList(
                    new Product(1, "托底数据-华为手机", 2, 5888D),
                    new Product(2, "托底数据-联想笔记本", 1, 6888D),
                    new Product(3, "托底数据-小米平板", 5, 2666D)
                );

            }

            @Override
            public Product selectProductById(Integer id) {
                logger.error("product-service 服务的selectProductById 方法出现异常，异常信息如下："+ throwable);
                return new Product(id, "托底数据", 2, 6666D);
            }
        };
    }
}
