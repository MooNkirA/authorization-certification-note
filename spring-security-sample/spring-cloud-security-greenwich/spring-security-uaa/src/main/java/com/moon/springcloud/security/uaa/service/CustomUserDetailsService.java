package com.moon.springcloud.security.uaa.service;

import com.alibaba.fastjson.JSON;
import com.moon.springcloud.security.uaa.dao.UserDao;
import com.moon.springcloud.security.uaa.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义 UserDetailsService 实现。
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-28 9:15
 * @description
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    /**
     * 自定义实现获取用户的逻辑
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据账号查询数据库
        UserDO user = userDao.getUserByUsername(username);

        if (user == null) {
            // 如果用户查不到，返回 null，由 Spring Security 框架 provider 来抛出异常
            return null;
        }

        // 根据用户id查询用户权限
        List<String> permissions = userDao.findPermissionsByUserId(user.getId());
        // 权限集合转数组
        String[] permissionArray = permissions.toArray(new String[permissions.size()]);
        // 将用户对象转成json字符串，设置到 username 属性中
        String principal = JSON.toJSONString(user);

        return User.withUsername(principal) // 设置用户对象的 json 字符串
                .password(user.getPassword()) // 设置密码
                .authorities(permissionArray) // 设置权限
                .build();
    }

}
