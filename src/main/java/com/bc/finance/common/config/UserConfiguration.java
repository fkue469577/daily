package com.bc.finance.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ace
 * @create 2017/12/26.
 */
@Configuration
public class UserConfiguration {
    @Value("${jwt.token-header}")
    private String userTokenHeader;

    @Value("${jwt.administrator}")
    private String administrator;

    public String getUserTokenHeader() {
        return this.userTokenHeader;
    }

    public String getAdministrator() {
        return this.administrator;
    }

}
