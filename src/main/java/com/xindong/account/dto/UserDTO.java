package com.xindong.account.dto;

import com.xindong.account.entity.Menu;
import lombok.Data;

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
    private List<Menu>  menus;
}
