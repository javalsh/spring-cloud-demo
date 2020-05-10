package com.lsh.service.impl;

import com.lsh.pojo.Product;
import com.lsh.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/8 16:43
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> selectProductList() {
        return Arrays.asList(
                new Product(1, "华为手机", 2, 5888D),
                new Product(2, "联想笔记本", 1, 6888D),
                new Product(3, "小米平板", 5, 2666D)
        );

    }

    @Override
    public List<Product> selectProductListByIds(List<Integer> ids) {
        List<Product> products = new ArrayList<>();
        ids.forEach(id -> products.add(new Product(id,"电视节"+id,1,111D)));
        return products;
    }

    @Override
    public Product selectProductById(Integer id) {
        return new Product(id,"冰箱",1,2666D);
    }
}
