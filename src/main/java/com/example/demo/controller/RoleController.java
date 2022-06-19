package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
//import com.example.demo.entity.User;
import java.util.List;

import com.example.demo.service.IRoleService;
import com.example.demo.entity.Role;

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
@RequestMapping("/role")
public class RoleController {


    @Resource
    private IRoleService roleService;

    @PostMapping
    public Result save(@RequestBody Role role)
         {
            return Result.success(roleService.saveOrUpdate(role));
         }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(roleService.removeById(id));
            }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids)
        {
            return Result.success(roleService.removeByIds(ids));
        }
    @GetMapping
    public Result findAll() {
            return Result.success(roleService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(roleService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam  String name) {
            QueryWrapper<Role> queryWrapper =new QueryWrapper<>();
            queryWrapper.like("name",name);
            return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }
}

