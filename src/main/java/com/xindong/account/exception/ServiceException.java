package com.xindong.account.exception;

import lombok.Getter;

/**
 * @Date: 2022/8/21/021 上午 9:18
 * @Author: taoyu
 * @Description:
 */
@Getter
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
