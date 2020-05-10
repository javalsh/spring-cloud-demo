package com.lsh.service;

import com.lsh.pojo.User;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:48
 */
// 声明需要调用的服务
@FeignClient(name = "service-provider")
public interface UserService {

    // 配置需要调用的服务地址及参数
    @GetMapping("/user/list")
    List<User> selectUserList();

    /**
     * 根据主键查询用户
     *
     * @return
     */
    @GetMapping("/user/{id}")
    User selectUserById(@PathVariable("id") Integer id);

    @GetMapping("/user/id")
    User selectUserByIdS(@RequestParam("id") Integer id);

    @GetMapping("/user/get")
    User getPojo(User user);

    /**
     * @description 查询用户
     * @param username
     * @return User
     * @author LSH
     * @date 2020/5/7 17:16
     */
    @PostMapping("/user/single")
    User selectUserByUsername(String username);

    /**
     * @description 新增用户
     * @param user
     * @return Map
     * @author LSH
     * @date 2020/5/7 17:17
     */
    @PostMapping("/user/save")
    Map<Object,Object> createUser(User user);
}
