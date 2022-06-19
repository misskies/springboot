package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
//import com.example.demo.entity.User;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.service.IMenuService;
import com.example.demo.entity.Menu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liu
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/menu")
public class MenuController {


    @Resource
    private IMenuService menuService;

    @PostMapping
    public  Result save(@RequestBody Menu menu)
         {
            return Result.success(menuService.saveOrUpdate(menu));
         }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(menuService.removeById(id));
            }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids)
        {
            return Result.success(menuService.removeByIds(ids));
        }
    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "")  String name) {

        QueryWrapper<Menu> queryWrapper =new QueryWrapper<>();
        queryWrapper.like("name",name);

        //查询所有数据
        List<Menu> list=menuService.list(queryWrapper);
        List<Menu> parentNode=list.stream().filter(menu -> menu.getPid()==null).collect(Collectors.toList());
        //找出一级菜单子菜单
        for (Menu menu:parentNode)
        {
            //pid=father.ID
            menu.setChilren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }

        return Result.success(parentNode);
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(menuService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam  String name) {
            QueryWrapper<Menu> queryWrapper =new QueryWrapper<>();
            queryWrapper.like("name",name);
            return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }
}

