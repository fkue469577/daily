package com.bc.finance.common.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.bc.finance.common.exception.auth.UserTokenException;
import com.bc.finance.common.exception.jwt.IJWTInfo;
import com.bc.finance.common.exception.jwt.JwtTokenUtil;
import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.common.handler.LoginHandler;
import com.bc.finance.common.holder.SpringContextHolder;
import com.bc.finance.common.msg.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class JwtResponseBody implements ResponseBodyAdvice<Object> {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, org.springframework.http.MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        //通过RequestContextHolder获取request
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if(body instanceof BaseResponse) {
            try {
                TokenUserHelper tokenUser = SpringContextHolder.getBean(TokenUserHelper.class);
                IJWTInfo jwtInfo = tokenUser.getTokenUser();

                JwtTokenUtil jwtTokenUtil = SpringContextHolder.getBean(JwtTokenUtil.class);
                if (jwtInfo.getExp() - System.currentTimeMillis() / 1000 < jwtTokenUtil.getExpire() / 2) {
                    LoginHandler loginHandler = SpringContextHolder.getBean(LoginHandler.class);
                    String token = loginHandler.generateToken(jwtInfo.getUserId());

                    Map<String, Object> map = BeanUtil.beanToMap(body);
                    map.put("authorization", token);
                    return map;
                }
            } catch (UserTokenException e) {

            }
        }
        return body;
    }
}