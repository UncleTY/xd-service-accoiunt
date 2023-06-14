package com.xindong.accounting.model.entity;

import lombok.Data;

/**
 * @Date: 2022/8/23/023 上午 10:47
 * @Author: taoyu
 * @Description:
 */
@Data
public class File {

    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Boolean isDelete;
    private Boolean enable;
}
