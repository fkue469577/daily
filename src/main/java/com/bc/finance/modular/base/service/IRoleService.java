package com.bc.finance.modular.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.base.entity.SysRole;

import java.util.Arrays;
import java.util.List;

public interface IRoleService extends IService<SysRole> {

    boolean removeById(Integer id);

    /**
     * 只获取 sys_enabled 为 真 的数据
     * @return
     */
    List<SysRole> listSystem();

    List<SysRole>  listByUserId(String userId);
}
