package com.bc.finance.common.result;

import java.util.List;

public class TableResponse<T> extends ObjectResponse {

    private long total;

    public TableResponse(long total, Object data) {
        super(data);
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public TableResponse setTotal(long total) {
        this.total = total;
        return this;
    }
}
