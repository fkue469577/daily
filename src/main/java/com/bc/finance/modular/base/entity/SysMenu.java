package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@TableName("base_menu")
@Data
public class SysMenu {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String code;

    private String pcode;

    private String pcodes;

    private String name;

    private String icon;

    private String url;

    private Integer num;

    private Integer levels;

    private Boolean ismenu;

    private String tips;

    private Boolean status;

    private Boolean isopen;

    private String layuiIcon;

    @TableField(exist = false)
    private List<SysMenu> children;

    public void addChildren(SysMenu menu) {
        if(children==null) children = new ArrayList<>();
        children.add(menu);
    }
}
