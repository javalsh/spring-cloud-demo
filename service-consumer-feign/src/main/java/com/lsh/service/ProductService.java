package com.lsh.service;

import com.lsh.fallback.ProductServiceFallback;
import com.lsh.fallback.ProductServiceFallbackFactory;
import com.lsh.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @Description 商品服务
 * @Author LSH
 * @Date 2020/5/8 16:41
 */
//@FeignClient(value = "service-provider",fallback= ProductServiceFallback.class)
@FeignClient(value = "service-provider",fallbackFactory= ProductServiceFallbackFactory.class)
public interface ProductService {

    /**
     * @Description 查询商品列表
     * @Params []
     * @Return java.util.List<com.lsh.pojo.Product>
     * @Author LSH
     * @Date 2020/5/8 16:42
     */
    @GetMapping("/product/list")
    List<Product> selectProductList();


    /**
     * @Description 根据主键查询商品
     * @Params [id]
     * @Return com.lsh.pojo.Product
     * @Author LSH
     * @Date 2020/5/8 16:42
     */
    @GetMapping("/product/{id}")
    Product selectProductById(@PathVariable("id") Integer id);
}
