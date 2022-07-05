package com.bc.finance.modular.base.mapper;

import com.bc.finance.modular.base.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * $!{table.comment} Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-06-02
 */
public interface MenuMapper extends BaseMapper<SysMenu> {

    @MapKey("id")
    List<Map> listByRoleId(@Param("roleId") Integer roleId);

    List<SysMenu> listByRoleList(@Param("role") List<String> role);

    List<Map> listSysMenuExpendRole(@Param("roleId") Integer roleId);
}
