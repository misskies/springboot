package com.example.demo.service;

import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liu
 * @since 2022-06-16
 */
public interface IUserService extends IService<User> {

    boolean login(UserDTO userDto);
}
