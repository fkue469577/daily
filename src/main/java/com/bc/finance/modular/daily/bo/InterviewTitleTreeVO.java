package com.bc.finance.modular.daily.bo;

import cn.hutool.core.bean.BeanUtil;
import com.bc.finance.common.utils.ConditionUtils;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyInterviewTitle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class InterviewTitleTreeVO {

    private String id;

    private String name;

    private String parentId;

    private List<InterviewTitleTreeVO> children;

    public void addChildren(InterviewTitleTreeVO vo) {
        if(children==null) {
            children = new ArrayList<>();
        }
        children.add(vo);
    }

    public static List<InterviewTitleTreeVO> from(List<DailyInterviewTitle> list) {
        List<InterviewTitleTreeVO> vos = BeanUtil.copyToList(list, InterviewTitleTreeVO.class);
        Map<String, InterviewTitleTreeVO> idMapTitle = vos.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        List<InterviewTitleTreeVO> root = vos.stream().filter(e -> StringUtils.isBlank(e.getParentId())).collect(Collectors.toList());
        vos.forEach(e-> ConditionUtils.trueRunnable(idMapTitle.containsKey(e.getParentId()), ()->idMapTitle.get(e.getParentId()).addChildren(e)));
        return root;
    }
}