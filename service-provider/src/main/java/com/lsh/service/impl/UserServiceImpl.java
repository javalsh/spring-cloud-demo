package com.lsh.service.impl;

import com.lsh.pojo.User;
import com.lsh.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:49
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * @description TODO
     * @params []
     * @return java.util.List<com.lsh.pojo.User>
     * @author LSH
     * @date 2020/5/8 13:09
     */
    @Override
    public List<User> selectUserList() {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return Arrays.asList(
                new User(1,"zhangsan",18),
                new User(2,"lisi",19),
                new User(3,"wangwu",20)
        );
    }

    /**
     * @description TODO
     * @params [user]
     * @return com.lsh.pojo.User
     * @author LSH
     * @date 2020/5/8 13:10
     */
    @Override
    public User getPojo(User user) {
        return user;
    }

    @Override
    public User selectUserById(Integer id) {
        return new User(id, "张三", 20);
    }

    @Override
    public User selectUserByUsername(String username) {
        return new User(1, username, 20);

    }

    /**
     * @Description TODO
     * @Params [user]
     * @Return java.util.Map<java.lang.Object,java.lang.Object>
     * @Author LSH
     * @Date 2020/5/8 13:11
     */
    @Override
    public Map<Object, Object> createUser(User user) {
        System.out.println(user);
        return new HashMap<Object, Object>() {{
            put("code", 200);
            put("message", "新增成功");
        }};

    }

}
