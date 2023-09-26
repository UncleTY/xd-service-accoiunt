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
	@Excel(name = "公司")
	private String company;
	@Excel(name = "地址")
	private String address;
	@Excel(name = "费用类型")
	private String feeType;
	@Excel(name = "开始日期")
	private String beginDate;
	@Excel(name = "结束日期")
	private String endDate;
	@Excel(name = "日期区间")
	private String dateDuration;
	@Excel(name = "发票号码")
	private String taxNo;
}
