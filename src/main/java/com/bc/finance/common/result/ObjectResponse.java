package com.bc.finance.common.result;

public class ObjectResponse<T> extends BaseResponse {

    Object data;

    public ObjectResponse(Object data) {
        super();
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public ObjectResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
