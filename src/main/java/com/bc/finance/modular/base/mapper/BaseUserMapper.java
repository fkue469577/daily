package com.bc.finance.modular.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc.finance.modular.base.entity.BaseUser;
import org.apache.ibatis.annotations.Select;

public interface BaseUserMapper extends BaseMapper<BaseUser> {
    @Select("select * from base_user where username=#(username)")
    BaseUser getByUsername(String username);
}
