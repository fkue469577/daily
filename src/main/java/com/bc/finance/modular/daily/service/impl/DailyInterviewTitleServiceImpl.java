package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.bo.InterviewTitleTreeVO;
import com.bc.finance.modular.daily.entity.DailyInterviewTitle;
import com.bc.finance.modular.daily.mapper.DailyInterviewTitleMapper;
import com.bc.finance.modular.daily.service.IDailyInterviewTitleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
@Service
public class DailyInterviewTitleServiceImpl extends ServiceImpl<DailyInterviewTitleMapper, DailyInterviewTitle> implements IDailyInterviewTitleService {

    @Resource
    DailyInterviewTitleMapper mapper;

    @Override
    @Cacheable("DailyInterviewTitleServiceImpl.listByParentId")
    public List<DailyInterviewTitle> listByParentId(String parentId) {
        if(StringUtils.isBlank(parentId)) {
            return new ArrayList<>();
        }
        List<DailyInterviewTitle> list = mapper.listByParentId(parentId);
        return list;
    }

    @Override
    public List<DailyInterviewTitle> listRoot() {
        List<DailyInterviewTitle> list = mapper.listRoot();
        return list;
    }

    @Override
    public List<InterviewTitleTreeVO> tree() {
        List<DailyInterviewTitle> list = this.list();
        List<InterviewTitleTreeVO> vos = InterviewTitleTreeVO.from(list);
        return vos;
    }
}
