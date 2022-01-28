package com.moon.spring.security.boot.dao;

import com.moon.spring.security.boot.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表持久层接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-28 16:33
 * @description
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据账号查询用户信息
     *
     * @param username
     * @return
     */
    public UserDO getUserByUsername(String username) {
        String sql = "select id,username,password,fullname,mobile from t_user where username = ?";
        // 连接数据库查询用户
        List<UserDO> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDO.class));
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

}
