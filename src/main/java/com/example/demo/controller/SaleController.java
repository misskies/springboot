package com.example.demo.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Goods;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
//import com.example.demo.entity.User;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.demo.service.ISaleService;
import com.example.demo.entity.Sale;

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
@RequestMapping("/sale")
public class SaleController {


    @Resource
    private ISaleService saleService;

    @PostMapping("/check")
    public  Result save(@RequestBody Goods goods)
    {
        Sale sale=new Sale();
        sale.setName(goods.getName());
        sale.setType(goods.getType());
        sale.setOprice(goods.getOprice());
        sale.setNum(goods.getInventory());
        sale.setSalemsg("销售信息");
        sale.setAllprice(sale.getNum()*sale.getOprice());
        return Result.success(saleService.saveOrUpdate(sale));
    }

    @PostMapping
    public  Result save(@RequestBody Sale sale)
    {
        sale.setSalemsg("销售信息");
        sale.setAllprice(sale.getNum()*sale.getOprice());
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
                           @RequestParam String name) {
        QueryWrapper<Sale> queryWrapper =new QueryWrapper<>();
        queryWrapper.like("name",name);
        return Result.success(saleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Sale> list =saleService.list();
        //写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id","ID");
        writer.addHeaderAlias("salemsg","信息");
        writer.addHeaderAlias("name","商品名");
        writer.addHeaderAlias("type","类型");
        writer.addHeaderAlias("oprice","售价");
        writer.addHeaderAlias("num","数量");
        writer.addHeaderAlias("allprice","总价");
        writer.addHeaderAlias("createTime","     创建时间    ");
        //一次性写出list内对象，强制输出标题
        writer.write(list,true);
        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("销售信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }
    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Sale> sales = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Sale sale = new Sale();
            sale.setName(row.get(1).toString());
            sale.setSalemsg("销售信息");
            sale.setType(row.get(2).toString());
            sale.setOprice(Double.parseDouble(row.get(3).toString()));
            sale.setNum(Integer.parseInt(row.get(4).toString()));
            sale.setAllprice(Double.parseDouble(row.get(5).toString()));
            sales.add(sale);
        }
        saleService.saveBatch(sales);
        return Result.success(true);
    }



















}

