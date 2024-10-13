package com.bc.finance.common.handler;

import com.bc.finance.common.config.UserConfiguration;
import com.bc.finance.common.exception.auth.UserPassInvalidException;
import com.bc.finance.common.exception.jwt.JWTInfo;
import com.bc.finance.common.exception.jwt.JwtTokenUtil;
import com.bc.finance.common.model.LoginModel;
import com.bc.finance.modular.base.entity.BaseUser;
import com.bc.finance.modular.base.entity.SysPermission;
import com.bc.finance.modular.base.entity.SysRole;
import com.bc.finance.modular.base.service.IBaseUserService;
import com.bc.finance.modular.base.service.IPermissionService;
import com.bc.finance.modular.base.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LoginHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserConfiguration userConfiguration;

    @Autowired
    IRoleService roleService;

    @Autowired
    IPermissionService permissionService;

    @Autowired
    IBaseUserService userService;

    public Map login(LoginModel loginModel) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        BaseUser user = userService.getByUsername(loginModel.getUsername());
        if(user == null || !user.getPassword().equals(loginModel.getPassword())) {
            throw new UserPassInvalidException();
        }

        Map result = new HashMap<>();
        String token = generateToken(user.getId());
        result.put("accessToken", "Bearer " + token);
        result.put("id", user.getId());
        result.put("name", user.getName());
        return result;
    }

    public String generateToken(String id) {
        BaseUser user = userService.getById(id);
        JWTInfo jwtInfo = new JWTInfo();
        jwtInfo.setUsername(user.getUsername());
        jwtInfo.setUserId(user.getId());
        jwtInfo.setName(user.getName());
        jwtInfo.setRole(roleService.listByUserId(user.getId()).stream().map(SysRole::getCode).collect(Collectors.toList()));
        jwtInfo.setPermission(permissionService.listByUserId(user.getId()).stream().map(SysPermission::getValue).collect(Collectors.toList()));
        try {
            return jwtTokenUtil.generateToken(jwtInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
