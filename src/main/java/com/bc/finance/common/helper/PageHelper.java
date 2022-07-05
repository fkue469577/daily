package com.bc.finance.common.helper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.utils.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class PageHelper {

    public static Page defaultPage() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String limitStr = request.getParameter("limit");
        int limit = Integer.parseInt(StringUtils.isNotEmpty(limitStr)? limitStr: "10");     //每页多少条数据
        String indexStr = request.getParameter("page");
        int index = Integer.parseInt(StringUtils.isNotEmpty(indexStr)? indexStr: "1");     //页数的偏移量(当前是第几页)
        Page page = new Page(index, limit);;
        return page;
    }

}
