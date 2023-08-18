package com.xindong.account.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xindong.account.common.Result;
import com.xindong.account.dto.AccountingDetailDTO;
import com.xindong.account.dto.CheckDTO;
import com.xindong.account.dto.CheckResultDTO;
import com.xindong.account.dto.ResultDTO;
import com.xindong.account.entity.AccountingDetail;
import com.xindong.account.entity.AccountingGroup;
import com.xindong.account.entity.Files;
import com.xindong.account.mapper.AccountingDetailMapper;
import com.xindong.account.mapper.AccountingGroupMapper;
import com.xindong.account.mapper.FileMapper;
import com.xindong.account.utils.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Date: 2022/8/23/023 上午 9:16
 * @Author: taoyu
 * @Description:
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String filePath;

    @Value("${server.ip}")
    private String serverIp;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private AccountingGroupMapper accountingGroupMapper;

    @Resource
    private AccountingDetailMapper accountingDetailMapper;


    /**
     * 文件上传接口
     *
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(filePath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) { // 文件已存在
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://" + serverIp + ":8082/file/" + fileUUID;
        }

        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        return url;
    }


    /**
     * 文件下载接口   http://43.138.229.147:8082/file/{fileUUID}
     *
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(filePath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uploadFile.getName(), "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5查询文件
     *
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.success(fileMapper.updateById(files));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from t_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    /**
     * 分页查询接口
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @RequestMapping("/getFile")
    public void getFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws Exception {
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName.trim(), "UTF-8"));
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath + fileName));
        OutputStream outputStream = response.getOutputStream();
        int i = bis.read(buffer);
        while (i != -1) {
            outputStream.write(buffer, 0, i);
            i = bis.read(buffer);
        }
        outputStream.flush();
        bis.close();
        outputStream.close();
    }

    @RequestMapping(value = "/check")
    public Result check(@RequestParam("file") MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        List<CheckDTO> subList = ExcelImportUtil.importExcel(file.getInputStream(), CheckDTO.class, importParams);
        subList = subList.stream().filter(item -> StringUtils.isNotBlank(item.getSubjectNo())).map(item -> {
            item.setSubjectNo(item.getSubjectNo().replace("，", ",").trim());
            return item;
        }).collect(Collectors.toList());
        Map<String, BigDecimal> subDataMap = removeMultiData(subList);
        importParams.setStartSheetIndex(1);
        List<CheckDTO> detailList = ExcelImportUtil.importExcel(file.getInputStream(), CheckDTO.class, importParams);
        detailList = detailList.stream().filter(item -> StringUtils.isNotBlank(item.getSubjectNo())).map(item -> {
            item.setSubjectNo(item.getSubjectNo().replace("，", ",").trim());
            return item;
        }).collect(Collectors.toList());
        Map<String, BigDecimal> detailDataMap = removeMultiData(detailList);
        List<CheckDTO> allList = new ArrayList<>();
        allList.addAll(subList);
        allList.addAll(detailList);
        Set<String> allSubjectNoList = allList.stream().filter(item -> StringUtils.isNotBlank(item.getSubjectNo()))
                .map(CheckDTO::getSubjectNo).collect(Collectors.toSet());
        List<ResultDTO> result = Lists.newArrayList();
        List<AccountingDetailDTO> detailResultList = Lists.newArrayList();
        AccountingGroup accountingGroup = new AccountingGroup();
        accountingGroup.setDate(DateUtils.getSysDate());
        accountingGroupMapper.insert(accountingGroup);
        allSubjectNoList.stream().forEach(subjectNo -> {
            ResultDTO resultDTO = new ResultDTO();
            AccountingDetailDTO detailDTO = new AccountingDetailDTO();
            resultDTO.setSubjectNo(subjectNo);
            detailDTO.setSubjectNo(subjectNo);
            BigDecimal subjectBalance = subDataMap.getOrDefault(subjectNo, BigDecimal.ZERO);
            subjectBalance = null == subjectBalance ? BigDecimal.ZERO : subjectBalance;
            BigDecimal detailBalance = detailDataMap.getOrDefault(subjectNo, BigDecimal.ZERO);
            detailBalance = null == detailBalance ? BigDecimal.ZERO : detailBalance;
            resultDTO.setSubjectBalance(subjectBalance.toPlainString());
            detailDTO.setSubjectBalance(subjectBalance);
            resultDTO.setDetailBalance(detailBalance.toPlainString());
            detailDTO.setDetailBalance(detailBalance);
            resultDTO.setDiffBalance(subjectBalance.subtract(detailBalance).toPlainString());
            detailDTO.setDiffBalance(subjectBalance.subtract(detailBalance));
            result.add(resultDTO);
            detailResultList.add(detailDTO);
            AccountingDetail accountingDetail = new AccountingDetail();
            accountingDetail.setGroupId(accountingGroup.getId());
            accountingDetail.setSubjectNo(resultDTO.getSubjectNo());
            accountingDetail.setSubjectBalance(subjectBalance);
            accountingDetail.setDetailBalance(detailBalance);
            accountingDetail.setDiffBalance(subjectBalance.subtract(detailBalance));
            accountingDetailMapper.insert(accountingDetail);
        });
        Workbook workbook = ExcelExportUtil.exportExcel(
                new ExportParams(null, null, "对比结果"),
                ResultDTO.class,
                result);
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = "对比结果" + time + ".xlsx";
        FileOutputStream fos = new FileOutputStream(filePath + fileName);
        workbook.write(fos);
        fos.close();
        return Result.success(new CheckResultDTO("http://" + serverIp + ":8082/file/" + fileName, detailResultList));
    }

    /**
     * 删除重复数据
     *
     * @param detailList
     * @return
     */
    private Map<String, BigDecimal> removeMultiData(List<CheckDTO> detailList) {
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
