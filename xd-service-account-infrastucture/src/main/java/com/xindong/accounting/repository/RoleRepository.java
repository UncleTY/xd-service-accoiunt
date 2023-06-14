package com.xindong.accounting.repository;

import java.util.List;

public interface RoleRepository {
	List<Integer> selectByRoleId(Integer roleId);
}
