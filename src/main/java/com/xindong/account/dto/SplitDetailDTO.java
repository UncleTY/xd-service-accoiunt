package com.xindong.account.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SplitDetailDTO implements Serializable {
	@Excel(name = "原始数据")
	private String originData;
	@Excel(name = "信息")
	private String message;
	@Excel(name = "物业费")
	private String feeType;
	@Excel(name = "开始日期")
	private String beginDate;
	@Excel(name = "结束日期")
	private String endDate;
	@Excel(name = "日期区间")
	private String dateDuration;

}
