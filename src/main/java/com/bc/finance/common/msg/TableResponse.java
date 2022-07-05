package com.bc.finance.common.msg;

import lombok.Data;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-06-14 22:40
 */
@Data
public class TableResponse<T> extends BaseResponse {

    long count;
    List<T> data;


    public TableResponse(int status, String message, long count, List<T> data) {
        super(status, message);
        this.count = count;
        this.data = data;
    }

    public TableResponse(long count, List<T> data) {
        this.count = count;
        this.data = data;
    }
}
