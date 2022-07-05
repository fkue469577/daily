package com.bc.finance.common.constant;

/**
 * Created by ace on 2017/8/29.
 */
public class CommonConstants {
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40101;
    public static final Integer EX_USER_PASS_INVALID_CODE = 40102;
    public static final Integer EX_CLIENT_LOGIN_INVALID_CODE = 40103;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40131;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    public static final Integer EX_CLIENT_BUSINESS_CODE = 40330;
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_NAME = "currentUser";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
    public static final String JWT_ID = "id";
    public static final String JWT_ROLE = "role";

    public static final Integer EX_NOT_EMPTY_CODE = 40601;
    public static final Integer EX_DUPLICATE_DATA_CODE = 40602;
    public static final Integer EX_UNLAWFUL_CODE = 40603;
    public static final Integer EX_ID_CARD_CODE = 40604;
    public static final Integer EX_BUSINESS_CODE = 40605;
    public static final Integer EX_EMPTY_USER = 40606;
    public static final Integer EX_VERSION_ERROR = 40607;
    public static final Integer EX_BD_ERROR = 40608;

    public static final Integer EX_NOT_FIND_WEB_CODE = 40401;

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";
}
