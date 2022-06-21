package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
//import com.example.demo.entity.User;
import java.util.List;

import com.example.demo.service.IGoodsService;
import com.example.demo.entity.Goods;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liu
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Resource
    private IGoodsService goodsService;

    @PostMapping
    public  Result save(@RequestBody Goods goods)
         {
            return Result.success(goodsService.saveOrUpdate(goods));
         }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(goodsService.removeById(id));
            }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids)
        {
            return Result.success(goodsService.removeByIds(ids));
        }
    @GetMapping
    public Result findAll() {
            return Result.success(goodsService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(goodsService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
            QueryWrapper<Goods> queryWrapper =new QueryWrapper<>();

            return Result.success(goodsService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }
}

