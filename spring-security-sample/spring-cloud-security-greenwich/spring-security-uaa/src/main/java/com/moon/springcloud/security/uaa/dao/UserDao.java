package com.moon.springcloud.security.uaa.dao;

import com.moon.springcloud.security.uaa.model.PermissionDO;
import com.moon.springcloud.security.uaa.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    /**
     * 根据用户id查询用户权限
     *
     * @param userId
     * @return
     */
    public List<String> findPermissionsByUserId(String userId) {
        String sql = "SELECT * FROM t_permission WHERE id IN(\n" +
                "\n" +
                "SELECT permission_id FROM t_role_permission WHERE role_id IN(\n" +
                "  SELECT role_id FROM t_user_role WHERE user_id = ? \n" +
                ")\n" +
                ")\n";

        List<PermissionDO> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDO.class));
        List<String> permissions = new ArrayList<>();
        list.forEach(c -> permissions.add(c.getCode()));
        return permissions;
    }

}
