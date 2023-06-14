package com.xindong.account.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.xindong.account.common.BusinessConstants;
import com.xindong.account.common.Result;
import com.xindong.account.converter.AccDetailConverter;
import com.xindong.account.dto.AccDetailDTO;
import com.xindong.account.dto.CheckResultDTO;
import com.xindong.account.dto.ResultDTO;
import com.xindong.account.entity.AccDetail;
import com.xindong.account.entity.AccGroup;
import com.xindong.account.executor.AccExecutor;
import com.xindong.account.mapper.AccDetailMapper;
import com.xindong.account.mapper.AccGroupMapper;
import com.xindong.account.utils.DateUtils;
import com.xindong.account.utils.FileUtils;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounting")
public class AccController {

	@Value("${files.upload.path}")
	private String filePath;

	@Value("${server.ip}")
	private String serverIp;

	@Value("${server.port}")
	private String serverPort;

	@Resource
	private AccGroupMapper accGroupMapper;

	@Resource
	private AccDetailMapper accDetailMapper;

	@Resource
	private AccExecutor accExecutor;


	@RequestMapping(value = "/check")
	@Transactional(rollbackFor = Exception.class)
	public Result check(@RequestParam("file") MultipartFile file) throws Exception {
		ImportParams importParams = new ImportParams();
		// 获取第一页
		importParams.setStartSheetIndex(0);
		Map<String, BigDecimal> subDataMap = FileUtils.getExcelData(file, importParams);
		// 获取第二页
		importParams.setStartSheetIndex(1);
		Map<String, BigDecimal> detailDataMap = FileUtils.getExcelData(file, importParams);
		// 获取所有类目
		Set<String> allSubjectNoList = Sets.newHashSet();
		allSubjectNoList.addAll(subDataMap.keySet());
		allSubjectNoList.addAll(detailDataMap.keySet());
		// 保存group
		AccGroup accGroup = saveAccGroup();
		List<ResultDTO> detailList = Lists.newArrayList();
		allSubjectNoList.stream().forEach(subjectNo -> {
			ResultDTO resultDTO = new ResultDTO();
			resultDTO.setSubjectNo(subjectNo);
			BigDecimal subjectBalance = subDataMap.getOrDefault(subjectNo, BigDecimal.ZERO);
			BigDecimal detailBalance = detailDataMap.getOrDefault(subjectNo, BigDecimal.ZERO);
			resultDTO.setSubjectBalance(subjectBalance.toPlainString());
			resultDTO.setDetailBalance(detailBalance.toPlainString());
			resultDTO.setDiffBalance(subjectBalance.subtract(detailBalance).toPlainString());
			detailList.add(resultDTO);
		});
		List<AccDetailDTO> accResult = saveAccDetails(accGroup.getId(), detailList);
		// 异步保存文件
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern(BusinessConstants.YYYY_MMDD_HHMMSS));
		String fileName = BusinessConstants.CHECK_RESULT + time + ".xlsx";
		accExecutor.saveFile(detailList, filePath, fileName);
		return Result.success(
				new CheckResultDTO(
						BusinessConstants.HTTP + serverIp + StrUtil.COLON + serverPort + "/file/" + fileName,
						accResult));
	}

	/**
	 * 保存明细
	 *
	 * @param id         组id
	 * @param detailList 明细数据
	 */
	private List<AccDetailDTO> saveAccDetails(Long id, List<ResultDTO> detailList) {
		List<AccDetail> accDetails = Optional.ofNullable(detailList).orElse(Lists.newArrayList()).stream().map(item -> {
			AccDetail accDetail = AccDetailConverter.INSTANCE.toDo(item, id);
			accDetailMapper.insert(accDetail);
			return accDetail;
		}).collect(Collectors.toList());
		return AccDetailConverter.INSTANCE.toAccDetailList(accDetails);
	}

	/**
	 * 保存组
	 *
	 * @return AccGroup
	 */
	private AccGroup saveAccGroup() {
		AccGroup accGroup = new AccGroup();
		accGroup.setDate(DateUtils.getSysDate());
		accGroup.setTime(DateUtils.getCurTime());
		accGroupMapper.insert(accGroup);
		return accGroup;
	}

	@GetMapping("/queryCheckList")
	public Result queryCheckList(@PathVariable Integer pageNum,
								 @PathVariable Integer pageSize,
								 @PathVariable Integer date,
								 @PathVariable Integer markFlag) {
		IPage<AccGroup> page = new Page<>(pageNum, pageSize);
		QueryWrapper<AccGroup> queryWrapper = new QueryWrapper<>();
		if (null != date) {
			queryWrapper.eq("date", date);
		}
		if (null != markFlag) {
			queryWrapper.eq("markFlag", markFlag);
		}
		return Result.success(accGroupMapper.selectPage(page, queryWrapper));
	}

	@PostMapping("/markAccount")
	public Result markAccount(@RequestParam String id, @RequestParam String remark) {
		AccGroup accGroup = accGroupMapper.selectById(id);
		if (StringUtils.isNotBlank(remark)) {
			accGroup.setRemark(remark);
		}
		accGroup.setMarkFlag(1);
		accGroupMapper.updateById(accGroup);
		return Result.success();
	}

	@PostMapping("/cancelMarkAccount")
	public Result cancelMarkAccount(@RequestParam String id, @RequestParam String remark) {
		AccGroup accGroup = accGroupMapper.selectById(id);
		if (StringUtils.isNotBlank(remark)) {
			accGroup.setRemark(remark);

		}
		accGroup.setMarkFlag(0);
		accGroupMapper.updateById(accGroup);
		return Result.success();
	}

	@GetMapping("/queryAccDetailsByGroupId")
	public Result markAccount(@PathVariable String id) {
		List<AccDetail> accDetails = new LambdaQueryChainWrapper<>(accDetailMapper)
				.eq(AccDetail::getGroupId, id).list();
		List<AccDetailDTO> result = new ArrayList<>();
		BeanUtils.copyProperties(accDetails, result);
		return Result.success(result);
	}
}
