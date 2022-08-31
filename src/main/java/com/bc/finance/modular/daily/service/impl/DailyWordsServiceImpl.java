package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.common.exception.business.BusinessException;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyWords;
import com.bc.finance.modular.daily.mapper.DailyWordsMapper;
import com.bc.finance.modular.daily.service.IDailyWordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单词表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-08-23
 */
@Service
public class DailyWordsServiceImpl extends ServiceImpl<DailyWordsMapper, DailyWords> implements IDailyWordsService {

    @Resource
    DailyWordsMapper mapper;


    @Override
    public List<Map> page(IPage page, Map param) {

        return mapper.page(page, param);
    }


    @Override
    public boolean save(DailyWords words) {
        if(StringUtils.isBlank(words.getId())) {
            this.insert(words);
        } else {
          this.update(words);
        }
        return true;
    }


    @Override
    public void insert(DailyWords words) {

        DailyWords temp = this.getByWord(words.getWord());
        if(temp!=null) {
            throw new BusinessException("该单词已经添加过, 请勿重复添加");
        }

        words.setId(ObjectId.getString());
        words.setCrtTime(LocalDateTime.now());
        super.save(words);
    }


    @Override
    public void update(DailyWords words) {

        super.updateById(words);
    }


    @Override
    public DailyWords getByWord(String word) {

        return mapper.getByWord(word);
    }
}
