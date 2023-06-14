package com.xindong.accounting.service;

import com.xindong.accounting.dataobject.base.BasePage;
import com.xindong.accounting.dataobject.request.role.RoleParam;
import com.xindong.accounting.dataobject.response.role.RoleDTO;

import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 4:44
 * @Author: taoyu
 * @Description:
 */
public interface RoleService {


	void setRoleMenu(Integer roleId, List<Integer> menuIds);

	List<Integer> getRoleMenu(Integer roleId);

	void saveOrUpdate(RoleParam role);

	void removeById(Integer id);

	void removeByIds(List<Integer> ids);

	List<RoleDTO> list();

	RoleDTO getById(Integer id);

	BasePage<RoleDTO> page(String name, Integer pageNum, Integer pageSize);
}
