package com.xindong.accounting.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date: 2022/8/23/023 上午 10:47
 * @Author: taoyu
 * @Description:
 */
@Data
@TableName("t_accounting_detail")
public class AccDetailDO {

	@TableId(type = IdType.AUTO)
	private Long id;
	private Long groupId;
	private String subjectNo;
	private BigDecimal subjectBalance;
	private BigDecimal detailBalance;
	private BigDecimal diffBalance;
}
