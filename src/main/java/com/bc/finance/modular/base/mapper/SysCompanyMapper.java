package com.bc.finance.modular.base.mapper;

import com.bc.finance.modular.base.entity.SysCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-06-17
 */
public interface SysCompanyMapper extends BaseMapper<SysCompany> {


    List<Map> listAllExpandSclId(String sclId);

    List<Map> listAllExpandDepartId(Long departId);
}
