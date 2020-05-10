package com.lsh.controller;

import com.lsh.pojo.User;
import com.lsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
