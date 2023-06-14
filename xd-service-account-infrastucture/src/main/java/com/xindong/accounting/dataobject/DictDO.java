package com.xindong.accounting.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Date: 2022/8/26/026 上午 10:31
 * @Author: taoyu
 * @Description:
 */
@Data
@TableName("t_dict")
public class DictDO {

    private String name;
    private String value;
    private String type;

}
