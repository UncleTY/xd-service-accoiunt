package com.xindong.account.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	public static Integer getSysDate() {
		LocalDateTime time = LocalDateTime.now();
		// 将时间转化为对应格式的字符串
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		return Integer.parseInt(time.format(formatter));
	}

	public static Integer getCurTime() {
		LocalTime time = LocalTime.now();
		// 将时间转化为对应格式的字符串
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		return Integer.parseInt(time.format(formatter));
	}
}
