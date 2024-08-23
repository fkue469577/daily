package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.bc.finance.modular.daily.mapper.DailyInterviewMapper;
import com.bc.finance.modular.daily.service.IDailyInterviewService;
import com.bc.finance.modular.daily.service.IDailyInterviewTitleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
@Service
public class DailyInterviewServiceImpl extends ServiceImpl<DailyInterviewMapper, DailyInterview> implements IDailyInterviewService {

    @Resource
    DailyInterviewMapper mapper;

    @Resource
    IDailyInterviewTitleService interviewTitleService;

    @Override
    public List paging(Page page, Map param) {
        List<Map<String, String>> list = mapper.paging(page, param);
        String name = param.get("name").toString().toLowerCase();
        list.forEach(e->{
            e.put("substr_context", e.get("substr_context").replaceAll("(\\<.*?\\>)|(\\<.*)", ""));
            e.put("name", addBackgroundColor(e.get("name"), name));
        });
        if(StringUtils.isNotBlank(name)) {
            list.forEach(e->{
                e.put("substr_context", addBackgroundColor(e.get("substr_context"), name));
            });
        }
        return list;
    }

    /**
     *
     * @param html
     * @return
     */
    public String addBackgroundColor(String html, String pattern) {
        Pattern compile = Pattern.compile("(?i)" + pattern);
        Matcher matcher = compile.matcher(html);
        List<int[]> list = new ArrayList<>();
        while(matcher.find()){
            int[] ints = new int[2];
            ints[0] = matcher.start();
            ints[1] = matcher.end();
            list.add(ints);
        }
        if(list.size()==0) {
            return html;
        }
        StringBuffer sb = new StringBuffer(list.size()>0? html.substring(0, list.get(0)[0]): "");
        for (int i = 0; i < list.size(); i++) {
            sb.append("<span style='background-color: yellow'>");
            sb.append(html.substring(list.get(i)[0], list.get(i)[1]));
            sb.append("</span>");
            sb.append(html.substring(list.get(i)[1], i<list.size()-1? list.get(i+1)[0]: html.length()));
        }
        return sb.toString();
    }

    @Override
    public void insert(DailyInterview interview) {
        interview.setCrtTime(LocalDateTime.now());
        interview.setId(ObjectId.getString());
        StringUtils.notBlankRunnable(interview.getContext(), ()-> interview.setSimplifyContext(interview.getContext().replaceAll("\\<.*?\\>", "")));
        super.save(interview);
    }


    @Override
    public void update(DailyInterview interview) {
        StringUtils.notBlankRunnable(interview.getContext(), ()-> interview.setSimplifyContext(interview.getContext().replaceAll("\\<.*?\\>", "")));
        super.updateById(interview);
    }
}
