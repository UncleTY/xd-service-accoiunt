package com.xindong.account.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.xindong.account.common.Result;
import com.xindong.account.dto.AccountingDetailDTO;
import com.xindong.account.dto.SplitDTO;
import com.xindong.account.dto.SplitDetailDTO;
import com.xindong.account.dto.SplitResultDTO;
import com.xindong.account.entity.AccountingDetail;
import com.xindong.account.entity.AccountingGroup;
import com.xindong.account.mapper.AccountingDetailMapper;
import com.xindong.account.mapper.AccountingGroupMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounting")
public class AccountingController {

	private static final String PATTERN_FEE = "^已传?([\\s\\S]*)(物业费)(\\d*)-?(\\d*)$";

	@Value("${files.upload.path}")
	private String filePath;


	@Value("${server.ip}")
	private String serverIp;

	@Resource
	private AccountingGroupMapper accountingGroupMapper;

	@Resource
	private AccountingDetailMapper accountingDetailMapper;


	@GetMapping("/queryCheckList")
	public Result queryCheckList(Integer pageNum,
								 Integer pageSize,
								 Integer date,
								 Integer markFlag) {
		IPage<AccountingGroup> page = new Page<>(pageNum, pageSize);
		QueryWrapper<AccountingGroup> queryWrapper = new QueryWrapper<>();
		if (null != date) {
			queryWrapper.eq("date", date);
		}
		if (null != markFlag) {
			queryWrapper.eq("markFlag", markFlag);
		}
		return Result.success(accountingGroupMapper.selectPage(page, queryWrapper));
	}

	@PostMapping("/markAccount")
	public Result markAccount(@RequestParam String id, @RequestParam String remark) {
		AccountingGroup accountingGroup = accountingGroupMapper.selectById(id);
		if (StringUtils.isNotBlank(remark)) {
			accountingGroup.setRemark(remark);
		}
		accountingGroup.setMarkFlag(1);
		accountingGroupMapper.updateById(accountingGroup);
		return Result.success();
	}

	@GetMapping("/queryAccountDetailsByGroupId")
	public Result markAccount(@PathVariable String id) {
		List<AccountingDetail> accountingDetails = new LambdaQueryChainWrapper<>(accountingDetailMapper)
				.eq(AccountingDetail::getGroupId, id).list();
		List<AccountingDetailDTO> result = new ArrayList<>();
		BeanUtils.copyProperties(accountingDetails, result);
		return Result.success(result);
	}


	@RequestMapping(value = "/dealMsg")
	public Result dealMsg(@RequestParam("file") MultipartFile file) throws Exception {
		ImportParams importParams = new ImportParams();
		importParams.setStartSheetIndex(0);
		List<SplitDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), SplitDTO.class, importParams);
		List<SplitDTO> filterList = Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
				.filter(item -> StringUtils.isNotBlank(item.getData())).collect(Collectors.toList());
		List<SplitDetailDTO> resultList = filterList.stream().map(item -> {
					SplitDetailDTO splitResult = getSplitResult(item.getData());
					return splitResult;
				}).collect(Collectors.toList());
		Workbook workbook = ExcelExportUtil.exportExcel(
				new ExportParams(null, null, "处理结果"),
				SplitDetailDTO.class,
				resultList);
		File saveFile = new File(filePath);
		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String fileName = "处理结果" + time + ".xlsx";
		FileOutputStream fos = new FileOutputStream(filePath + fileName);
		workbook.write(fos);
		fos.close();
		return Result.success(new SplitResultDTO("http://" + serverIp + ":8082/file/" + fileName, resultList));
	}

	private SplitDetailDTO getSplitResult(String message) {
		SplitDetailDTO splitResult = new SplitDetailDTO();
		splitResult.setOriginData(message);
		Pattern pattern = Pattern.compile(PATTERN_FEE);
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			splitResult.setMessage(matcher.group(1));
			splitResult.setFeeType(matcher.group(2));
			splitResult.setBeginDate(matcher.group(3));
			splitResult.setEndDate(matcher.group(4));
			if (StringUtils.isNotBlank(matcher.group(3)) && StringUtils.isNotBlank(matcher.group(4))) {
				splitResult.setDateDuration(matcher.group(3) + "-" + matcher.group(4));
			}
		}
		return splitResult;
	}
}
