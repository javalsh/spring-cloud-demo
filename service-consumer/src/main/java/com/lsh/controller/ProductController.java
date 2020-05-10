package com.lsh.controller;

import com.lsh.pojo.Product;
import com.lsh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/8 17:15
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 查询商品列表
     *
     * @return
     */
    @GetMapping("/list")
    public List<Product> selectProductList() {
        return productService.selectProductList();
    }

    /**
     * 根据多个主键查询商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/listByIds")
    public List<Product> selectProductListByIds(@RequestParam("id") List<Integer> ids) {
        return productService.selectProductListByIds(ids);
    }

    /**
     * 根据主键查询商品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Product selectProductById(@PathVariable("id") Integer id) {
        return productService.selectProductById(id);
    }
    /**
     * 根据主键查询商品
     * 请求合并实例
     * @param
     * @return
     */
    @GetMapping("/single")
    public void selectFutureProductById() {
        // 模拟同一时间用户发起多个请求。
        Future<Product> p1 = productService.selectFutureProductById(1);
        Future<Product> p2 = productService.selectFutureProductById(2);
        Future<Product> p3 = productService.selectFutureProductById(3);
        Future<Product> p4 = productService.selectFutureProductById(4);
        Future<Product> p5 = productService.selectFutureProductById(5);
        try {
            System.out.println(p1.get());
            System.out.println(p2.get());
            System.out.println(p3.get());
            System.out.println(p4.get());
            System.out.println(p5.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
