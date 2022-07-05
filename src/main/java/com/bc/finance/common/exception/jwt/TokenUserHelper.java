package com.bc.finance.common.exception.jwt;

import com.bc.finance.common.config.UserConfiguration;
import com.bc.finance.common.exception.auth.UserTokenException;
import com.bc.finance.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenUserHelper {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserConfiguration userConfiguration;

    /**
     * 获取token允许返回为空,  因为正常的请求已经过滤掉token过期的数据
     * null: 兼容 feign 请求
     */
    public IJWTInfo getTokenUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = request.getHeader(userConfiguration.getUserTokenHeader());
        if(StringUtils.isBlank(token)) {
            String key = userConfiguration.getUserTokenHeader();
            token = String.valueOf(request.getSession().getAttribute(key));
        }
        IJWTInfo infoFromToken = null;
        try {
            infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        } catch (Exception e) {
            throw new UserTokenException();
        }
        return infoFromToken;
    }

    public boolean isAdmin() {
        IJWTInfo infoFromToken = getTokenUser();
        String administrator = userConfiguration.getAdministrator();
        return infoFromToken.getRole().stream().anyMatch(e->administrator.equals(e));
    }

    public boolean isSchool() {
        IJWTInfo infoFromToken = getTokenUser();
        return StringUtils.isNotBlank(infoFromToken.getSclId());
    }

    public boolean isLogin() {
        IJWTInfo user = this.getTokenUser();
        return user!=null;
    }

    public String getSclId() {
        IJWTInfo user = this.getTokenUser();
        return user.getSclId();
    }

    public Long getUserId() {
        IJWTInfo user = this.getTokenUser();
        return user.getUserId();
    }
}
