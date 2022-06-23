package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Constants;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.Menu;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.TokenUtils;
//import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liu
 * @since 2022-06-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log  log=Log.get();
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private IMenuService menuService;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Override
    public UserDTO login(UserDTO userDto) {
        User one =getUserInfo(userDto);
        if(one!=null) {

            BeanUtil.copyProperties(one,userDto,true);
            String token=TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDto.setToken(token);
            String role = one.getRole();
            List<Menu> roleMenus = getRoleMenus(role);
            userDto.setMenus(roleMenus);
            return  userDto;
        }
        else
        {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if(one ==null)
        {
            one =new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);
        }
        else
        {
            throw new ServiceException(Constants.CODE_600,"用户名已存在");
        }
        return one;
    }

    private User getUserInfo(UserDTO userDTO)
    {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one ;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e)
        {
            log.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
    private List<Menu> getRoleMenus(String roleFlag)
    {
        Integer roleId =roleMapper.selectByFlag(roleFlag);
        List<Integer> menuIds =roleMenuMapper.selectByRoleId(roleId);

        //查出所有菜单
        List<Menu> menus=menuService.findMenus("");
        List<Menu> roleMeuns= new ArrayList<>();
        //选出用户菜单
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getId()))
            {
                roleMeuns.add(menu);
            }
            List<Menu > children =menu.getChildren();
            children.removeIf(child ->!menuIds.contains(child.getId()));
        }
        return (roleMeuns);

    }
}
