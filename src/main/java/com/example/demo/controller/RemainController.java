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
import com.example.demo.service.IGoodsService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
//import com.example.demo.entity.User;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.demo.service.IRemainService;
import com.example.demo.entity.Remain;

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
@RequestMapping("/remain")
public class RemainController {


    @Resource
    private IRemainService remainService;
    @Resource
    private IGoodsService goodsService;

    @PostMapping
    public  Result save(@RequestBody Remain remain)
         {
            return Result.success(remainService.saveOrUpdate(remain));
         }
    @PostMapping("/check")
    public  Result save(@RequestBody Goods goods)
    {
        Remain remain=new Remain();
        Goods good =goodsService.getById(goods.getId());
        if(good!=null)
        {
            remain.setMsgtype("库存信息修改");
        }
        else
        {
            remain.setMsgtype("新增库存信息");
        }
        remain.setName(goods.getName());
        remain.setType(goods.getType());
        remain.setIprice(goods.getIprice());
        remain.setOprice(goods.getOprice());
        remain.setInventory(goods.getInventory());
        return Result.success(remainService.saveOrUpdate(remain));
    }

    @PostMapping("/dele")
    public  Result del(@RequestBody Goods goods)
    {
        Remain remain=new Remain();
        remain.setMsgtype("删除库存信息");
        remain.setName(goods.getName());
        remain.setType(goods.getType());
        remain.setIprice(goods.getIprice());
        remain.setOprice(goods.getOprice());
        remain.setInventory(goods.getInventory());
        return Result.success(remainService.saveOrUpdate(remain));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(remainService.removeById(id));
            }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids)
        {
            return Result.success(remainService.removeByIds(ids));
        }
    @GetMapping
    public Result findAll() {
            return Result.success(remainService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(remainService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
            QueryWrapper<Remain> queryWrapper =new QueryWrapper<>();
            queryWrapper.like("name",name);
            return Result.success(remainService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Remain> list =remainService.list();
        //写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id","ID");
        writer.addHeaderAlias("msgtype","信息类别");
        writer.addHeaderAlias("name","商品名");
        writer.addHeaderAlias("type","商品类型");
        writer.addHeaderAlias("iprice","进价");
        writer.addHeaderAlias("oprice","售价");
        writer.addHeaderAlias("inventory","数量");
        writer.addHeaderAlias("createTime","     创建时间    ");
        //一次性写出list内对象，强制输出标题
        writer.write(list,true);
        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("入库信息", "UTF-8");
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
        List<Remain> remains = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Remain remain = new Remain();
            remain.setMsgtype(row.get(1).toString());
            remain.setName(row.get(2).toString());

            remain.setType(row.get(3).toString());

            remain.setIprice(Double.parseDouble(row.get(4).toString()));
            remain.setOprice(Double.parseDouble(row.get(5).toString()));

            remain.setInventory(Integer.parseInt(row.get(6).toString()));
            remains.add(remain);
        }
        remainService.saveBatch(remains);
        return Result.success(true);
    }
}

