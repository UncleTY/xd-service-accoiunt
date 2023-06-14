package com.xindong.account.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.common.collect.Maps;
import com.xindong.account.dto.CheckDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileUtils {

	/**
	 * 获取excel数据
	 *
	 * @param file
	 * @param importParams
	 * @return
	 * @throws Exception
	 */
	public static Map<String, BigDecimal> getExcelData(MultipartFile file, ImportParams importParams) throws Exception {
		// 读取excel数据
		List<CheckDTO> subList = ExcelImportUtil.importExcel(file.getInputStream(), CheckDTO.class, importParams);
		subList = subList.stream().filter(item -> StringUtils.isNotBlank(item.getSubjectNo())).map(item -> {
			item.setSubjectNo(item.getSubjectNo().replace("，", ",").trim());
			return item;
		}).collect(Collectors.toList());
		// 去重
		Map<String, BigDecimal> dataMap = removeMultiData(subList);
		return dataMap;
	}

	/**
	 * 删除重复数据
	 *
	 * @param detailList
	 * @return
	 */
	private static Map<String, BigDecimal> removeMultiData(List<CheckDTO> detailList) {
		Map<String, BigDecimal> map = Maps.newHashMap();
		detailList.stream().forEach(item -> {
			if (org.apache.commons.lang3.StringUtils.isBlank(item.getSubjectNo())) {
				return;
			}
			if (map.containsKey(item.getSubjectNo())) {
				map.put(item.getSubjectNo(), map.get(item.getSubjectNo()).add(item.getBalance()));
			} else {
				map.put(item.getSubjectNo(), item.getBalance());
			}
		});
		return map;
	}
}
