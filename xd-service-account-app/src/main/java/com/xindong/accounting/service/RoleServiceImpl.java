package com.xindong.accounting.service;

import cn.hutool.core.collection.CollUtil;
import com.xindong.accounting.dataobject.base.BasePage;
import com.xindong.accounting.dataobject.request.role.RoleParam;
import com.xindong.accounting.dataobject.response.role.RoleDTO;
import com.xindong.accounting.mapper.RoleMenuMapper;
import com.xindong.accounting.model.entity.RoleMenu;
import com.xindong.accounting.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 4:44
 * @Author: taoyu
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	RoleMenuMapper roleMenuMapper;

	@Resource
	MenuService menuService;

	@Resource
	RoleRepository roleRepository;

	@Transactional
	@Override
	public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
		//先删除当前角色id所有绑定的关系
		roleMenuMapper.deleteByRoleId(roleId);

		//再把前端拿过来的菜单id数组绑定到当前的这个角色id上去
		List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
		for (Integer menuId : menuIds) {
			Menu menu = menuService.getById(menuId);
			if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) {//二级菜单，并且传过来的menuid数组里面没有他的父级id
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menu.getPid());
				roleMenuMapper.insert(roleMenu);
				menuIdsCopy.add(menu.getPid());
			}
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenuMapper.insert(roleMenu);
		}
	}

	@Override
	public List<Integer> getRoleMenu(Integer roleId) {
		return roleRepository.selectByRoleId(roleId);
	}

	@Override
	public void saveOrUpdate(RoleParam role) {
		roleRepository.saveOrUpdate(role);
	}

	@Override
	public void removeById(Integer id) {
		roleRepository.removeById(id);
	}

	@Override
	public void removeByIds(List<Integer> ids) {
		roleRepository.removeByIds(ids);

	}

	@Override
	public List<RoleDTO> list() {
		return roleRepository.list();
	}

	@Override
	public RoleDTO getById(Integer id) {
		return roleRepository.getById(id);;
	}

	@Override
	public BasePage<RoleDTO> page(String name, Integer pageNum, Integer pageSize) {
		return null;
	}
}
