package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.daily.entity.DailyCompany;
import com.bc.finance.modular.daily.mapper.DailyCompanyMapper;
import com.bc.finance.modular.daily.service.IDailyCompanyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
@Service
public class DailyCompanyServiceImpl extends ServiceImpl<DailyCompanyMapper, DailyCompany> implements IDailyCompanyService {

}
