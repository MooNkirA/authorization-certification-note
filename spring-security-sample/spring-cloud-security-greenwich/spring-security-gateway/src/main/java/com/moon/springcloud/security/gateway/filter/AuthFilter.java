package com.moon.springcloud.security.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.moon.springcloud.security.gateway.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zuul 前置过滤器实现，完成当前登录用户信息提取，并转发给微服务
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-01 14:45
 * @description
 */
public class AuthFilter extends ZuulFilter {

    /* 过滤器类型 */
    @Override
    public String filterType() {
        return "pre"; // 前置过滤器
    }

    /* 过滤器加载顺序 */
    @Override
    public int filterOrder() {
        return 0;
    }

    /* 是否加载过滤器 */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        // 从安全上下文中获取用户身份对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2Authentication)) {
            return null;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        // 取出用户身份信息
        String principal = userAuthentication.getName();

        // 取出用户权限
        List<String> authorities = new ArrayList<>();
        // 从 userAuthentication 取出权限，放在 authorities
        userAuthentication.getAuthorities().stream().forEach(c -> authorities.add(((GrantedAuthority) c).getAuthority()));

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonToken = new HashMap<>(requestParameters);
        if (userAuthentication != null) {
            jsonToken.put("principal", principal);
            jsonToken.put("authorities", authorities);
        }

        // 把身份信息和权限信息放在 json 中，加入 http 的 header 中,转发给微服务
        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));

        return null;
    }

}
