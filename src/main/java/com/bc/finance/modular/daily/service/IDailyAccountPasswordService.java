package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.daily.entity.DailyAccountPassword;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号密码表 服务类
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
public interface IDailyAccountPasswordService extends IService<DailyAccountPassword> {

    List page(IPage page, Map param);

    boolean save(DailyAccountPassword ap);

    void delete(String id);

    DailyAccountPassword get(String id);
}
