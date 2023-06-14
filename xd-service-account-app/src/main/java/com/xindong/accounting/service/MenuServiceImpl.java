package com.xindong.accounting.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xindong.account.entity.Menu;
import com.xindong.account.mapper.MenuMapper;
import com.xindong.accounting.dataobject.MenuDO;
import com.xindong.accounting.dataobject.response.menu.MenuDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2022/8/25/025 下午 5:37
 * @Author: taoyu
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Override
    public List<MenuDTO> findMenus(String name) {
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        // 查询所有数据
        List<Menu> list = list(queryWrapper);
        // 找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
