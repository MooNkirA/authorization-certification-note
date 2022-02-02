package com.moon.security.session.interceptor;

import com.moon.security.session.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 拦截器，实现简单授权拦截
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-24 22:00
 * @description
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 请求前置拦截逻辑。在此方法中校验用户请求的url是否在用户的权限范围内
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户身份信息
        Object object = request.getSession().getAttribute(UserDto.SESSION_USER_KEY);

        if (object == null) {
            // 用户无登陆
            responseContent(response, "请登录");
            return false;
        }

        UserDto userDto = (UserDto) object;
        // 请求的url
        String requestURI = request.getRequestURI();
        if (userDto.getAuthorities().contains("p1") && requestURI.contains("/check/p1")) {
            return true;
        }
        if (userDto.getAuthorities().contains("p2") && requestURI.contains("/check/p2")) {
            return true;
        }
        responseContent(response, "没有权限，拒绝访问");

        return false;
    }

    /**
     * 响应处理
     *
     * @param response
     * @param msg
     */
    private void responseContent(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(msg);
        writer.close();
    }

}
