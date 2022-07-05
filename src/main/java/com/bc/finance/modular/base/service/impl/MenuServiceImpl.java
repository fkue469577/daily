package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.base.entity.SysMenu;
import com.bc.finance.modular.base.mapper.MenuMapper;
import com.bc.finance.modular.base.service.IMenuService;
import com.bc.finance.modular.base.service.IPermissionService;;
import com.bc.finance.modular.base.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* <p>
    * 菜单表 服务实现类
    * </p>
*
* @author pcc
* @since 2022-06-02
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, SysMenu> implements IMenuService {

    @Resource
    private MenuMapper mapper;

    @Resource
    TokenUserHelper userHelper;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    IRoleMenuService roleMenuService;

    @Override
    public List listMenuListExpendRole(Integer roleId) {
        List<Map> menuList = mapper.listByRoleId(roleId);
        List<Map> permission = permissionService.getPermissionByRoldid(roleId);

        List<Map> root = menuList.stream().filter(e -> StringUtils.isBlank(e.get("pcode"))).collect(Collectors.toList());
        Map<String, Map> code2Menu = menuList.stream().collect(Collectors.toMap(e -> String.valueOf(e.get("code")), e -> e));
        Map<Integer, Map> id2Menu = menuList.stream().collect(Collectors.toMap(e -> StringUtils.parseInt(e.get("id")), e -> e));

        menuList.stream().filter(e->!StringUtils.isBlank(e.get("pcode"))).forEach(e->{
            Map menu = code2Menu.get(e.get("pcode"));
            if(menu!=null) {
                List node = (List)menu.get("node");
                if(node==null) {
                    node = new ArrayList();
                    menu.put("node", node);
                }
                node.add(e);
            }
        });

        permission.forEach(e->{
            Map menu = id2Menu.get(e.get("menu_id"));
            if(menu!=null) {
                List node = (List)menu.get("permission");
                if(node==null) {
                    node = new ArrayList();
                    menu.put("permission", node);
                }
                node.add(e);
            }
        });
        return root;
    }

    @Override
    public List<SysMenu> listByRoleList(List<String> role) {
        if(role==null || role.size()==0) return new ArrayList<>();

        List<SysMenu> menuList = mapper.listByRoleList(role);

        List<SysMenu> rootList = menuList.stream().filter(e -> StringUtils.isBlank(e.getPcode())).collect(Collectors.toList());
        Map<String, SysMenu> code2Object = menuList.stream().collect(Collectors.toMap(e -> e.getCode(), e -> e, (a, b)->a=b));
        menuList.stream()
                .filter(e -> StringUtils.isNotBlank(e.getPcode()) && code2Object.containsKey(e.getPcode()))
                .forEach(e->code2Object.get(e.getPcode()).addChildren(e));
        return rootList;
    }

    @Override
    public List<Map> listSystemMenuListExpendRole(Integer roleId) {
        List<Map> menuList = mapper.listSysMenuExpendRole(roleId);
        List<Map> permissionList = permissionService.listSysPermissionExpendRole(roleId);

        List<Map> root = menuList.stream().filter(e -> StringUtils.isBlank(e.get("pcode"))).collect(Collectors.toList());
        Map<String, Map> code2Menu = menuList.stream().collect(Collectors.toMap(e -> String.valueOf(e.get("code")), e -> e));
        Map<Integer, Map> id2Menu = menuList.stream().collect(Collectors.toMap(e -> StringUtils.parseInt(e.get("id")), e -> e));

        menuList.stream().filter(e->!StringUtils.isBlank(e.get("pcode"))).forEach(e->{
            Map menu = code2Menu.get(e.get("pcode"));
            if(menu!=null) {
                List node = (List)menu.get("node");
                if(node==null) {
                    node = new ArrayList();
                    menu.put("node", node);
                }
                node.add(e);
            }
        });

        permissionList.forEach(e->{
            Map menu = id2Menu.get(e.get("menu_id"));
            if(menu!=null) {
                List node = (List)menu.get("permission");
                if(node==null) {
                    node = new ArrayList();
                    menu.put("permission", node);
                }
                node.add(e);
            }
        });
        return root;
    }
}
