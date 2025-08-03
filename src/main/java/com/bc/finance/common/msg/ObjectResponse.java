package com.bc.finance.common.msg;

import lombok.Data;

/**
 * Created by Ace on 2017/6/11.
 */
@Data
public class ObjectResponse<T> extends BaseResponse {

    T data;

    public ObjectResponse(){}
    public ObjectResponse(T data) {
        this.data = data;
    }
    public static <T> ObjectResponse data(T data) {
        ObjectResponse<T> response = new ObjectResponse();
        response.setData(data);
        return response;
    }
    public T getData() {
        return data;
    }

}
