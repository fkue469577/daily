package com.bc.finance.modular.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.base.entity.BaseUser;
import com.bc.finance.modular.base.entity.SysMenu;

public interface IBaseUserService extends IService<BaseUser> {
    BaseUser getByUsername(String username);
}
