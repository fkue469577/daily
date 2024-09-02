package com.bc.finance.modular.daily.service;

import com.bc.finance.modular.daily.bo.InterviewTitleTreeVO;
import com.bc.finance.modular.daily.entity.DailyInterviewTitle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
public interface IDailyInterviewTitleService extends IService<DailyInterviewTitle> {

    List<DailyInterviewTitle> listByParentId(String parentId);

    List<DailyInterviewTitle> listRoot();

    List<InterviewTitleTreeVO> tree();
}
