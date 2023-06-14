//package com.xindong.accounting.controller;
//
//
//import com.xindong.accounting.dataobject.base.BasePage;
//import com.xindong.accounting.dataobject.base.Result;
//import com.xindong.accounting.dataobject.response.AccDetailDTO;
//import com.xindong.accounting.mapper.AccDetailMapper;
//import com.xindong.accounting.mapper.AccGroupMapper;
//import com.xindong.accounting.model.entity.AccDetail;
//import com.xindong.accounting.model.entity.AccGroup;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/accounting")
//public class AccController {
//
//	@Resource
//	private AccGroupMapper accGroupMapper;
//
//
//	@Resource
//	private AccDetailMapper accDetailMapper;
//
//	@GetMapping("/queryCheckList")
//	public Result queryCheckList(@PathVariable Integer pageNum,
//								 @PathVariable Integer pageSize,
//								 @PathVariable Integer date,
//								 @PathVariable Integer markFlag) {
//		BasePage<AccGroup> page = new BasePage<>(pageNum, pageSize);
//		QueryWrapper<AccGroup> queryWrapper = new QueryWrapper<>();
//		if (null != date) {
//			queryWrapper.eq("date", date);
//		}
//		if (null != markFlag) {
//			queryWrapper.eq("markFlag", markFlag);
//		}
//		return Result.success(accGroupMapper.selectPage(page, queryWrapper));
//	}
//
//	@PostMapping("/markAccount")
//	public Result markAccount(@RequestParam String id, @RequestParam String remark) {
//		AccGroup AccGroup = accGroupMapper.selectById(id);
//		if (StringUtils.isNotBlank(remark)) {
//			AccGroup.setRemark(remark);
//		}
//		AccGroup.setMarkFlag(1);
//		accGroupMapper.updateById(AccGroup);
//		return Result.success();
//	}
//
//	@GetMapping("/queryAccountDetailsByGroupId")
//	public Result markAccount(@PathVariable String id) {
//		List<AccDetail> accDetails = new LambdaQueryChainWrapper<>(accDetailMapper)
//				.eq(AccDetail::getGroupId, id).list();
//		List<AccDetailDTO> result = new ArrayList<>();
//		BeanUtils.copyProperties(accDetails, result);
//		return Result.success(result);
//	}
//}
