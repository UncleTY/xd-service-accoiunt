package com.xindong.accounting.dataobject.response.role;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Date: 2022/8/25/025 下午 4:38
 * @Author: taoyu
 * @Description:
 */
@Getter
@Setter
public class RoleDTO implements Serializable {


    private Integer id;

    private String name;

    private String description;

    private String flag;
}
