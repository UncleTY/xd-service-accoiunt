package com.xindong.account.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AccountingCheckResultDTO {

	private Long id;
	private Integer date;
	private Integer markFlag;
	private String remark;
	private List<AccountingDetailDTO> detailList;
}
