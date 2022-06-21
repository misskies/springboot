package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
//import com.example.demo.entity.User;
import java.util.List;

import com.example.demo.service.ISaleService;
import com.example.demo.entity.Sale;

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
@RequestMapping("/sale")
public class SaleController {


    @Resource
    private ISaleService saleService;

    @PostMapping
    public  Result save(@RequestBody Sale sale)
         {
             sale.setSalemsg("销售信息");
             sale.setAllprice(sale.getNum()*sale.getIprice());
            return Result.success(saleService.saveOrUpdate(sale));
         }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(saleService.removeById(id));
            }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids)
        {
            return Result.success(saleService.removeByIds(ids));
        }
    @GetMapping
    public Result findAll() {
            return Result.success(saleService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(saleService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestBody String name) {
            QueryWrapper<Sale> queryWrapper =new QueryWrapper<>();
            queryWrapper.like("name",name);
            return Result.success(saleService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }
}

