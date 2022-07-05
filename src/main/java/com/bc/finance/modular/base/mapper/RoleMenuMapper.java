package com.bc.finance.modular.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc.finance.modular.base.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Delete("delete from sys_role_menu where role_id=#{roleId} and menu_id=#{menuId}")
    void removeByRoleIdAndMenuId(Integer roleId, Integer menuId);

    @Select("select * from sys_role_menu where role_id=#{roleId}")
    List<SysRoleMenu> listByRoleId(Integer roleId);
}
