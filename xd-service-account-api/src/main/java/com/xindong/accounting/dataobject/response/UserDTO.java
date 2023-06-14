package com.xindong.accounting.dataobject.response;

import com.xindong.accounting.dataobject.response.menu.MenuDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2022/8/20/020 上午 9:48
 * @Author: taoyu
 * @Description:接收前端登入请求的参数
 */
@Data
public class UserDTO {
	private String username;
	private String password;
	private String nickname;
	private String avatarUrl;
	private String token;
	private String role;
	private Date createTime;
	private List<MenuDTO> menus;
}
