package com.lsh.service;

import com.lsh.pojo.Product;

import java.util.List;

/**
 * @Description 商品服务
 * @Author LSH
 * @Date 2020/5/8 16:41
 */
public interface ProductService {

    /**
     * @Description 查询商品列表
     * @Params []
     * @Return java.util.List<com.lsh.pojo.Product>
     * @Author LSH
     * @Date 2020/5/8 16:42
     */
    List<Product> selectProductList();

    /**
     * @Description 根据多个主键查询商品
     * @Params [ids]
     * @Return java.util.List<com.lsh.pojo.Product>
     * @Author LSH
     * @Date 2020/5/8 16:42
     */
    List<Product> selectProductListByIds(List<Integer> ids);

    /**
     * @Description 根据主键查询商品
     * @Params [id]
     * @Return com.lsh.pojo.Product
     * @Author LSH
     * @Date 2020/5/8 16:42
     */
    Product selectProductById(Integer id);

}
