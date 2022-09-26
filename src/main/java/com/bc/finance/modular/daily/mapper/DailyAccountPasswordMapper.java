package com.bc.finance.modular.daily.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.modular.daily.entity.DailyAccountPassword;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号密码表 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
public interface DailyAccountPasswordMapper extends BaseMapper<DailyAccountPassword> {

    List<Map> page(IPage page, @Param("param") Map param);
}
