package com.bc.finance.modular.base.mapper;

import com.bc.finance.modular.base.entity.SysCompanyDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 责任主体与专业关联表 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-06-17
 */
public interface SysCompanyDepartmentMapper extends BaseMapper<SysCompanyDepartment> {

    @Select("select count(1) num from sys_company_department where company_id=#{companyId}")
    int countByCompanyId(String companyId);

    @Select("select * from sys_company_department where department_id=#{departmentId}")
    List<SysCompanyDepartment> listByDepartId(Long departmentId);

    @Select("select * from sys_company_department where department_id=#{departmentId} and is_default=1")
    SysCompanyDepartment getDefaultByDepartId(Long departmentId);
}
