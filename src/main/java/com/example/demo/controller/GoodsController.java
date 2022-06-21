package com.example.demo.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
//import com.example.demo.entity.User;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.demo.service.IGoodsService;
import com.example.demo.entity.Goods;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
            @GetMapping("/export")
            public void export(HttpServletResponse response) throws Exception{
                List<Goods> list =goodsService.list();
                //写出到浏览器
                ExcelWriter writer = ExcelUtil.getWriter(true);
                writer.addHeaderAlias("id","ID");
                writer.addHeaderAlias("name","商品名称");
                writer.addHeaderAlias("type","商品类型");
                writer.addHeaderAlias("iprice","入库价");
                writer.addHeaderAlias("oprice","零售价");  
                writer.addHeaderAlias("inventory","库存");
                     writer.addHeaderAlias("avatar_url","样图");
                //一次性写出list内对象，强制输出标题
                writer.write(list,true);
                //设置浏览器响应的格式
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
                String fileName = URLEncoder.encode("货物信息", "UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

                ServletOutputStream out=response.getOutputStream();
                writer.flush(out,true);
                out.close();
                writer.close();

            }

     @PostMapping("/import")
        public Result imp(MultipartFile file) throws Exception {
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
    //        List<User> list = reader.readAll(User.class);

            // 方式2：忽略表头的中文，直接读取表的内容
            List<List<Object>> list = reader.read(1);
            List<Goods> goods = CollUtil.newArrayList();
            for (List<Object> row : list) {
                Goods good = new Goods();
                good.setName(row.get(1).toString());
                good.setType(row.get(2).toString());
                good.setIprice(Double.parseDouble(row.get(3).toString()));
                good.setOprice(Double.parseDouble(row.get(4).toString()));
                good.setInventory(Integer.parseInt(row.get(5).toString()));
                goods.add(good);
            }
            goodsService.saveBatch(goods);
            return Result.success(true);
        }










}

