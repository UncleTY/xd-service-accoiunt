package com.xindong.accounting.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Date: 2022/8/23/023 上午 10:47
 * @Author: taoyu
 * @Description:
 */
@Data
@TableName("t_accounting_group")
public class AccGroupDO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer date;
    private Integer markFlag;
    private String remark;
}
