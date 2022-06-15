package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.lang.UsesSunHttpServer;

import java.util.List;
import java.util.stream.BaseStream;

public interface UserMapper extends BaseMapper<User> {
//
//    @Select("SELECT * FROM sys_user")
//    List<User> findAll();
//
//    @Insert("insert into sys_user(username,password,nickname,email,phone,address) " +
//            "values(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
//    int insert(User user);
//    int update(User user);
//
//    @Delete("delete from sys_user where id=#{id}")
//    Integer deleteId(@Param("id") Integer id);
//
//    @Select("select * from sys_user where username like concat('%',#{username},'%') limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize,String username);
//
//    @Select("select count(*) from sys_user where username like concat('%',#{username},'%')")
//    Integer selectTotal(String username);
}
