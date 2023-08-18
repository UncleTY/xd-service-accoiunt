package com.xindong.account.dto;

import lombok.Data;

/**
 * @Date: 2022/8/23/023 上午 10:47
 * @Author: taoyu
 * @Description:
 */
@Data
public class AccountingGroupDTO {

	private Long id;
	private Integer date;
	private Integer markFlag;
	private String remark;
}
