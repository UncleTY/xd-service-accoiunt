package com.xindong.accounting.service;

import com.xindong.accounting.dataobject.response.menu.MenuDTO;

import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 5:38
 * @Author: taoyu
 * @Description:
 */
public interface MenuService {

	List<MenuDTO> findMenus(String name);
}
