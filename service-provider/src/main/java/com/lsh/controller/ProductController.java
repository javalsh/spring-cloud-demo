package com.lsh.controller;

import com.lsh.pojo.Product;
import com.lsh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/5/8 16:49
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> selectProductList() {
        System.out.println("……………………product-list…………………………");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productService.selectProductList();
    }

    @GetMapping("/listByIds")
    public List<Product> selectProductListByIds(@RequestParam("id") List<Integer> ids) {
        return productService.selectProductListByIds(ids);
    }

    @GetMapping("/{id}")
    public Product selectProductById(@PathVariable("id") Integer id) {
        System.out.println("……………………product-id…………………………");
        return productService.selectProductById(id);
    }


}
