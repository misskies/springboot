package com.example.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleMenu;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liu
 * @since 2022-06-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;
    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        //先删除当前角色所有绑定关系再添加
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if(menu.getPid()!=null&&!menuIdsCopy.contains(menu.getPid()))//二级菜单
            {
                //补上父级id
                RoleMenu roleMenu= new RoleMenu();
                roleMenu.setRoleID(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
           RoleMenu roleMenu= new RoleMenu();
           roleMenu.setRoleID(roleId);
           roleMenu.setMenuId(menuId);
           roleMenuMapper.insert(roleMenu);

        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {

        return roleMenuMapper.selectByRoleId(roleId);
    }

}
