package com.bc.finance.modular.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc.finance.modular.base.entity.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<SysRole> {

    @Select("select * from sys_role sr where exists (select 1 from usm_admin_role uar where admin_id=#{sclAdminId} and sr.id=uar.role_id)")
    List<SysRole> listByAdminId(Long sclAdminId);

    List<SysRole> listBySclId(String sclId);

    List<SysRole> listSystem();

    @Select("select * from sys_role sr where exists (select 1 from base_user_role bur where bur.user_id=#(userId) and sr.id=bur.role_id))")
    List<SysRole> listByUserId(String userId);
}
