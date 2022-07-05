package com.bc.finance.common.msg;

import lombok.Data;

/**
 * Created by ace on 2017/8/23.
 */
@Data
public class BaseResponse {
    private static BaseResponse baseResponse;

    private int code = 200;
    private String msg = "操作成功";

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse() {
    }

    public static BaseResponse success() {
        if(baseResponse==null) baseResponse = new BaseResponse();
        return baseResponse;
    }

}
