package com.bc.finance.common.result;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.bc.finance.modular.CaptchaController")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof IPage) {
            IPage page = (IPage) body;
            return new TableResponse(page.getTotal(), page.getRecords());
        }

        if(body instanceof String) {
            return JSON.toJSONString(new ObjectResponse(body));
        }

        if(body != null) {
            return new ObjectResponse(body);
        }

        return BaseResponse.success();
    }
}
