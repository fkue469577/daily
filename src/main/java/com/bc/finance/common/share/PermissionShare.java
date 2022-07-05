package com.bc.finance.common.share;

import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.common.holder.SpringContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionShare {

    public static boolean hasPermission(String permission) {
        TokenUserHelper userHelper = SpringContextHolder.getBean(TokenUserHelper.class);
        List<String> permissionList = userHelper.getTokenUser().getPermission();

        return permissionList.contains(permission);
    }

}
