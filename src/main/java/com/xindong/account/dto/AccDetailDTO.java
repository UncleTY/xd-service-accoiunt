package com.xindong.account.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date: 2022/8/23/023 上午 10:47
 * @Author: taoyu
 * @Description:
 */
@Data
public class AccDetailDTO {

    private Long id;
    private Long groupId;
    private String subjectNo;
    private BigDecimal subjectBalance;
    private BigDecimal detailBalance;
    private BigDecimal diffBalance;
}
