package com.xindong.accounting.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	public static Integer getSysDate() {
		LocalDateTime time = LocalDateTime.now();
		// 将时间转化为对应格式的字符串
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return Integer.parseInt(time.format(formatter));
	}
}
