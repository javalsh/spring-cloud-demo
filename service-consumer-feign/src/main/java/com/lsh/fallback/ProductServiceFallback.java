package com.lsh.fallback;

import com.lsh.pojo.Product;
import com.lsh.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 服务熔断降级处理
 * @Author LSH
 * @Date 2020/5/9 15:24
 */
@Service
public class ProductServiceFallback implements ProductService {
    @Override
    public List<Product> selectProductList() {
        return Arrays.asList(
            new Product(1, "托底数据-华为手机", 2, 5888D),
            new Product(2, "托底数据-联想笔记本", 1, 6888D),
            new Product(3, "托底数据-小米平板", 5, 2666D)
        );

    }

    @Override
    public Product selectProductById(Integer id) {
        return new Product(id, "托底数据", 2, 6666D);
    }
}
