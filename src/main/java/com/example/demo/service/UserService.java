package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user) {
//        if (user.getId()==null)
//        {
//            return save(user);//mybatis-plus 插入
//        }
//        else
//        {
//            return updateById(user);
//        }
        return saveOrUpdate(user);
    }
//    @Autowired
//    private UserMapper userMapper;
//
//    public int save(User user)
//    {
//        if(user.getId()==null)
//        {
//            return userMapper.insert(user);//没有则新增
//        }
//        else//更新
//        {
//            return userMapper.update(user);
//        }
//    }
}
