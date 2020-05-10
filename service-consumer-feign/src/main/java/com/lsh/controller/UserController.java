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
 * @Date 2020/2/17 17:35
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 用户管理
     *
     * @return
     */
    @GetMapping("/manage")
    public List<User> userManage() {
        return userService.selectUserList();
    }
    @GetMapping("/{id}")
    public User selectUserById(@PathVariable("id") Integer id) {
        return userService.selectUserById(id);
    }
    @GetMapping("/id")
    public User selectUserByIdS(Integer id) {
        return userService.selectUserByIdS(id);
    }
    @GetMapping("/get")
    public User getPojo(User user) {
        return userService.getPojo(user);
    }


    @PostMapping("/info")
    public User selectUserByUsername(String username) {
        return userService.selectUserByUsername(username);
    }

    @PostMapping("/save")
    public Map<Object,Object> createUser(User user) {
        return userService.createUser(user);
    }
}
