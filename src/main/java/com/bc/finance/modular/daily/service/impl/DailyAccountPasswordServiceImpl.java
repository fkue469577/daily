package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyAccountPassword;
import com.bc.finance.modular.daily.mapper.DailyAccountPasswordMapper;
import com.bc.finance.modular.daily.service.IDailyAccountPasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号密码表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
@Service
public class DailyAccountPasswordServiceImpl extends ServiceImpl<DailyAccountPasswordMapper, DailyAccountPassword> implements IDailyAccountPasswordService {

    @Resource
    DailyAccountPasswordMapper mapper;

    @Autowired
    DailyCompanyServiceImpl companyService;

    @Override
    public List<Map> page(IPage page, Map param) {
        List<Map> list = mapper.page(page, param);

        return list;
    }


    @Override
    public boolean save(DailyAccountPassword ap) {
        if(StringUtils.isBlank(ap.getId())) {
            ap.setId(ObjectId.getString());
            ap.setCrtTime(LocalDateTime.now());
            super.save(ap);
        } else {
            super.updateById(ap);
        }
        return false;
    }


    @Override
    public void delete(String id) {

        super.removeById(id);
    }


    @Override
    public DailyAccountPassword get(String id) {

        return super.getById(id);
    }
}
