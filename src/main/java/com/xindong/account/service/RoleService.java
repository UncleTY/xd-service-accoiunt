package com.xindong.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xindong.account.entity.Role;

import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 4:44
 * @Author: taoyu
 * @Description:
 */
public interface RoleService extends IService<Role> {


    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
