package com.xindong.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xindong.account.entity.Menu;

import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 5:38
 * @Author: taoyu
 * @Description:
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
