package com.bc.finance.common.constant;

import com.bc.finance.common.utils.RsaKeyHelper;

import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-06-14 8:36
 */
public class UserConstant {
    public final static int PW_ENCORDER_SALT = 12;
    public final static String USER_TOKEN_HEADER = "Authorization";
    public final static int EXPIRE = 14400;
    public final static String RSA_SECRET = "xx1WET12^%3^(WE45";

    public static byte[] userPubKey;
    public static byte[] userPriKey;

    static {
        try {
            userPubKey = RsaKeyHelper.toBytes("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPRfc4A5SAmpi3sSWqRBk/QL2UQ/uvn/BsbaNoa5mz1AaWJS3eP7lSBFezPWbCskIhq6WFThKLtGDlofO+QVb7i3h0hRL5Z+4zuAbOSY4iMv1vYryHAyjPGX2vubGlr3yrrjt3QDYwbTuj853g8zgZ0e5G0LhdclWZLHEPaFpFIwIDAQAB");
            userPriKey = RsaKeyHelper.toBytes("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM9F9zgDlICamLexJapEGT9AvZRD+6+f8Gxto2hrmbPUBpYlLd4/uVIEV7M9ZsKyQiGrpYVOEou0YOWh875BVvuLeHSFEvln7jO4Bs5JjiIy/W9ivIcDKM8Zfa+5saWvfKuuO3dANjBtO6PzneDzOBnR7kbQuF1yVZkscQ9oWkUjAgMBAAECgYArXRjmmCEmcrGGpMIbiBm8GQBQl5R9Xrm/BYUNYI2MiMxD901MVQqhdRUgA7WPABpDzxRW+kl4/ujSwforkKHwqAtHeb834L3QBicjvZ9Tkzo0GErt+wOsZhDI3CxogRLD4vL9Xqh6k/53dm0sx6LohKFtQOQabRmWupz5Jc3wMQJBAOqtVOaKRL6ycOzPdFeHAK3hvhtdD4P65WLOoQ8XYYwq/qXlt1Z6wxH73mWO520v5AiwAP5GY5rerp+4AtsNyUkCQQDiGzaHPPbB9bYPjK4xrTA48+X1oVVd84B86XF+WNRwLt+nxSeGl1mhgf1dbUwlmfkJ8JWUSEcBdKQ6/HTQ1KcLAkBsJfjsTWgk4aL83xXkiEid2Vx8y8QstGElycebZtEDgYTc+yIkbmqbTRFOiC7KuLlD76hlhha89kZPQMPAI3hRAkBQUhx3xEdgNZocQfxrdzuHL9VEAbDitCqztPX1TTcCNxSKc7YL0N4tSpEnzDjdrqnSRx3L1DUtJjNlJOOWf8RrAkEAu5juRM2OfYUXXe7p3MjwutiIS+XodK/tl4q/4eQsHj/Fos27MlBCtORYCyNXF/Y0u5hOQuUqf35DK2U/f5ICBg==");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
