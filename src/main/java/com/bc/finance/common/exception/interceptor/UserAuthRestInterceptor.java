package com.bc.finance.common.exception.interceptor;

import com.bc.finance.common.config.UserConfiguration;
import com.bc.finance.common.exception.auth.UserTokenException;
import com.bc.finance.common.exception.jwt.IJWTInfo;
import com.bc.finance.common.exception.jwt.JwtTokenUtil;
import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ace on 2017/9/10.
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(UserAuthRestInterceptor.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserConfiguration userConfiguration;

    @Resource
    TokenUserHelper userHelper;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 配置该注解，说明不进行用户拦截
        try {
            userHelper.isLogin();
        } catch (Exception e) {
            String requestedWith = request.getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(requestedWith)) {
                throw new UserTokenException();
            } else {
                response.sendRedirect("/login");
            }
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
