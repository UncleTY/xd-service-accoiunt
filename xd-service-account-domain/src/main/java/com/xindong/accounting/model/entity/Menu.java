package com.xindong.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2022/8/25/025 下午 5:33
 * @Author: taoyu
 * @Description:
 */
@Getter
@Setter
public class Menu implements Serializable {

    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;

    private List<Menu> children;

    private Integer pid;

    private String pagePath;

}
