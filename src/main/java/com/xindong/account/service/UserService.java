package com.xindong.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xindong.account.dto.UserDTO;
import com.xindong.account.dto.UserPasswordDTO;
import com.xindong.account.entity.User;

/**
 * @Date: 2022/8/12/012 下午 4:49
 * @Author: taoyu
 * @Description:
 */
public interface UserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
