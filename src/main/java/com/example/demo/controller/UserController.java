package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public  boolean save(@RequestBody User user)
    {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> findAll()
    {
        return userService.list();
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id)
    {
        return userService.removeById(id);
    }
    //
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
//    @RequestParam String username)
//    {
//        pageNum = (pageNum-1)*pageSize;
//        Integer total=userMapper.selectTotal(username);
//        List<User> data=userMapper.selectPage(pageNum,pageSize,username);
//        Map<String,Object> res= new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//
//    }
    @GetMapping("/page")//mybatis-plus
    public IPage<User> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "")String username,
                                @RequestParam(defaultValue = "") String nickname,
                                @RequestParam(defaultValue = "") String address)
    {
        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(!"".equals(username))
        {
            queryWrapper.like("username",username);
        }
        if(!"".equals(nickname))
        {
            queryWrapper.like("nickname",nickname);
        }
        if(!"".equals(address))
        {
            queryWrapper.like("address",address);
        }
        return userService.page(page,queryWrapper);

    }
}
