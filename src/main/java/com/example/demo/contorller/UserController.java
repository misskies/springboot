package com.example.demo.contorller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController//
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @PostMapping
    public  Integer save(@RequestBody User user)
    {
        return userService.save(user);
    }

    @GetMapping
    public List<User> findAll()
    {
        return userMapper.findAll();
    }
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id)
    {
        return userMapper.deleteId(id);
    }
    //
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
    @RequestParam String username)
    {
        pageNum = (pageNum-1)*pageSize;
        Integer total=userMapper.selectTotal(username);
        List<User> data=userMapper.selectPage(pageNum,pageSize,username);
        Map<String,Object> res= new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;

    }
}
