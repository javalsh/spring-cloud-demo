package com.lsh.controller;

import com.lsh.pojo.User;
import com.lsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> selectuserList(){
        return userService.selectUserList();
    }

    @GetMapping("/{id}")
    public User selectUserById(@PathVariable("id") Integer id) {
        return userService.selectUserById(id);
    }

    @GetMapping("/id")
    public User selectUserByIdS(@RequestParam Integer id) {
        return userService.selectUserById(id);
    }
    @GetMapping("/get")
    public User getPojo(@RequestBody User user) {
        return userService.getPojo(user);
    }

    /**
     * @description 根据用户名查询用户
     * @param username
     * @return User
     * @author LSH
     * @date 2020/5/7 17:09
     */
    @PostMapping("/single")
    public User selectUserByUsername(@RequestBody String username) {
        return userService.selectUserByUsername(username);
    }

    /**
     * @description 创建用户
     * @param user
     * @return Map
     * @author LSH
     * @date 2020/5/7 17:12
     */
    @PostMapping("/save")
    public Map<Object,Object> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}
