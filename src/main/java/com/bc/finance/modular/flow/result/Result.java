package com.bc.finance.modular.flow.result;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(){
        return restResult(200,"成功",null);
    }
    public static <T> Result<T> success(T data){
        return restResult(200,"成功",data);
    }
    public static <T> Result<T> success(String msg){
        return restResult(200,msg,null);
    }
    public static <T> Result<T> fail(String msg){
        return restResult(500,msg,null);
    }

    private static <T> Result<T> restResult(int code,String msg,T data){
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}