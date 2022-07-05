package com.bc.finance.common.exception.jwt;

import com.bc.finance.common.utils.UUIDUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ace on 2017/9/10.
 */
@Data
public class JWTInfo implements Serializable, IJWTInfo {
    private String id;
    private String username;
    private String name;
    private String userId;
    private String tokenId;
    private List<String> role;
    private List<String> permission;

    public JWTInfo() {}
    public JWTInfo(String username, String userId, String name) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.tokenId = UUIDUtils.generateShortUuid();
    }

    public JWTInfo(String username, String userId, String name, String tokenId) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.tokenId = tokenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JWTInfo jwtInfo = (JWTInfo) o;

        if (username != null ? !username.equals(jwtInfo.username) : jwtInfo.username != null) {
            return false;
        }
        return userId != null ? userId.equals(jwtInfo.userId) : jwtInfo.userId == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String getUniqueName() {
        return this.username;
    }

}
