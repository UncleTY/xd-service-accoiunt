package com.xindong.accounting.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Date: 2022/8/12/012 下午 4:25
 * @Author: taoyu
 * @Description:
 */
@Data
public class User {
	private Integer id;
	private String username;
	private String nickname;
	private String password;
	private String email;
	private String address;
	private String phone;
	private Date createTime;
	private String avatarUrl;
	private String role;
}
