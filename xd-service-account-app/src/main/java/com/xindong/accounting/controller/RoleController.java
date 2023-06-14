package com.xindong.accounting.controller;

import com.xindong.accounting.dataobject.base.Result;
import com.xindong.accounting.dataobject.request.role.RoleParam;
import com.xindong.accounting.service.RoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 4:33
 * @Author: taoyu
 * @Description:
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;

	//增加或更新
	@PostMapping
	public Result save(@RequestBody RoleParam role) {
		roleService.saveOrUpdate(role);
		return Result.success();
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Integer id) {
		roleService.removeById(id);
		return Result.success();
	}

	@PostMapping("del/batch")
	public Result deleteByBatch(@RequestBody List<Integer> ids) {
		roleService.removeByIds(ids);
		return Result.success();
	}

	@GetMapping
	public Result findAll() {
		return Result.success(roleService.list());
	}

	@GetMapping("/{id}")
	public Result findOne(@PathVariable Integer id) {
		return Result.success(roleService.getById(id));
	}

	@GetMapping("/page")
	public Result findPage(@RequestParam String name,
						   @RequestParam Integer pageNum,
						   @RequestParam Integer pageSize) {
//        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", name);
//        queryWrapper.orderByDesc("id");
		return Result.success(roleService.page(name, pageNum, pageSize));
	}


	/**
	 * @param roleId  角色id
	 * @param menuIds 菜单id数组
	 * @return
	 */
	@PostMapping("/roleMenu/{roleId}")
	public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
		roleService.setRoleMenu(roleId, menuIds);
		return Result.success();
	}

	@GetMapping("/roleMenu/{roleId}")
	public Result getRoleMenu(@PathVariable Integer roleId) {
		return Result.success(roleService.getRoleMenu(roleId));
	}

}
