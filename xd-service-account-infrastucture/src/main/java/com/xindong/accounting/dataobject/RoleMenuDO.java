package com.xindong.accounting.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Date: 2022/8/26/026 下午 12:47
 * @Author: taoyu
 * @Description:
 */
@TableName("t_role_menu")
@Data
public class RoleMenuDO {

    private Integer roleId;
    private Integer menuId;
}
