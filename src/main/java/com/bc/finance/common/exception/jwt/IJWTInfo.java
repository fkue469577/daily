package com.bc.finance.common.exception.jwt;

import java.util.List;

/**
 * Created by ace on 2017/9/10.
 */
public interface IJWTInfo {
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    String getId();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * tokenId
     * @return
     */
    String getTokenId();

    List<String> getRole();

    List<String> getPermission();

    String getSclId();

    /**
     * 获取用户ID
     * @return
     */
    Long getUserId();
}
