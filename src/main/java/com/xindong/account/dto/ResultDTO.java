package com.xindong.account.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDTO implements Serializable {

	@Excel(name = "科目编码", orderNum = "1")
	private String subjectNo;

	@Excel(name = "科目表金额", orderNum = "2", type = 10)
	private String subjectBalance;

	@Excel(name = "明细表金额", orderNum = "3", type = 10)
	private String detailBalance;

	@Excel(name = "金额差额", orderNum = "4", type = 10)
	private String diffBalance;


	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	public String getSubjectBalance() {
		return subjectBalance;
	}

	public void setSubjectBalance(String subjectBalance) {
		this.subjectBalance = subjectBalance;
	}

	public String getDetailBalance() {
		return detailBalance;
	}

	public void setDetailBalance(String detailBalance) {
		this.detailBalance = detailBalance;
	}

	public String getDiffBalance() {
		return diffBalance;
	}

	public void setDiffBalance(String diffBalance) {
		this.diffBalance = diffBalance;
	}
}
