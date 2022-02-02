package com.moon.springcloud.security.order.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moon.springcloud.security.order.model.UserDTO;
import com.moon.springcloud.security.order.utils.EncryptUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 鉴权拦截
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-01 15:20
 * @description
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头(header)中获取 token
        String token = httpServletRequest.getHeader("json-token");
        if (token != null) {
            // 解析 token
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            // 将token转成json对象
            JSONObject jsonObject = JSON.parseObject(json);
            // 创建自定义的用户身份信息实例
            // UserDTO userDTO = new UserDTO();
            String principal = jsonObject.getString("principal");
            // userDTO.setUsername(principal);
            // 改造后，username 是 UserDTO 对象的 json 字符串
            UserDTO userDTO = JSON.parseObject(principal, UserDTO.class);

            // 用户权限
            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
            String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
            // 新建并将用户信息和权限填充到用户身份token对象中
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDTO, null, AuthorityUtils.createAuthorityList(authorities));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            // 将authenticationToken填充到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 让过滤器链继续执行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
