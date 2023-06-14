package com.xindong.accounting.service;


import com.xindong.accounting.dataobject.response.UserDTO;
import com.xindong.accounting.dataobject.response.UserPasswordDTO;

import java.util.List;

/**
 * @Date: 2022/8/12/012 下午 4:49
 * @Author: taoyu
 * @Description:
 */
public interface UserService {
	UserDTO getById(Integer userId);

	UserDTO login(UserDTO userDTO);

	UserDTO register(UserDTO userDTO);

	void updatePassword(UserPasswordDTO userPasswordDTO);

	Integer saveOrUpdate(UserDTO user);

	List<UserDTO> list();

	Integer removeById(Integer id);
}
