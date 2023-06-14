package com.xindong.account.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AccCheckResultDTO {

	private Long id;
	private Integer date;
	private Integer markFlag;
	private String remark;
	private List<AccDetailDTO> detailList;
}
