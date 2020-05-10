package com.lsh.service;

import com.lsh.pojo.User;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 16:48
 */
public interface UserService {

    List<User> selectUserList();

    User getPojo(User user);
    /**
     * @description 根据主键查询参数
     * @param id
     * @return 
     * @author LSH
     * @date 2020/5/7 17:54
     */
    User selectUserById(Integer id);




    /**
     * @description 根据用户名查询用户
     * @param username
     * @return
     * @author LSH
     * @date 2020/5/7 17:04
     */
    User selectUserByUsername(String username);
    /**
     * @description TODO
     * @param user
     * @return
     * @author LSH
     * @date 2020/5/7 17:05
     */
    Map<Object, Object> createUser(User user);

}
