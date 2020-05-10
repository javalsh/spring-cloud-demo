package com.lsh.service.impl;

import com.lsh.pojo.User;
import com.lsh.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:49
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> selectUserList() {
        return Arrays.asList(
                new User(1,"zhangsan",18),
                new User(2,"lisi",19),
                new User(3,"wangwu",20)
        );
    }
}
