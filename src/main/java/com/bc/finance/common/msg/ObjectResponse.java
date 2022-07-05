package com.bc.finance.common.msg;

/**
 * Created by Ace on 2017/6/11.
 */
public class ObjectResponse<T> extends BaseResponse {

    T data;

    public ObjectResponse(){}
    public ObjectResponse(T data) {
        this.data = data;
    }
    public ObjectResponse data(T data) {
        this.setData(data);
        return this;
    }
    public T getData() {
        return data;
    }

    public ObjectResponse setData(T data) {
        this.data = data;
        return this;
    }


}
