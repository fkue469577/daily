package com.bc.finance.modular.base.mapper;

import com.bc.finance.modular.base.entity.SysCompanySchool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 责任主体与学校关联表 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-06-17
 */
public interface SysCompanySchoolMapper extends BaseMapper<SysCompanySchool> {

    @Select("select count(1) num from sys_company_school where company_id=#{companyId}")
    int countByCompanyId(String companyId);

    @Select("select * from sys_company_school where scl_id=#{sclId}")
    List<SysCompanySchool> listBySclId(String sclId);

    @Select("select * from sys_company_school where scl_id=#{sclId} and is_default=1")
    SysCompanySchool getDefaultBySclId(String sclId);
}
