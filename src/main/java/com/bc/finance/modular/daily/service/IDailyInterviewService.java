package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.daily.entity.DailyNotes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
public interface IDailyInterviewService extends IService<DailyInterview> {

    List paging(Page page, Map param);

    void insert(DailyInterview interview);

    void update(DailyInterview interview);
}
