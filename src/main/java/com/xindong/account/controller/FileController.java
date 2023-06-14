package com.xindong.account.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xindong.account.common.BusinessConstants;
import com.xindong.account.common.Result;
import com.xindong.account.entity.Files;
import com.xindong.account.executor.AccExecutor;
import com.xindong.account.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

	@Value("${files.template}")
	private String templateName;

	@Value("${server.ip}")
	private String serverIp;

	@Resource
	private FileMapper fileMapper;

	@Resource
	private AccExecutor accExecutor;


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
			url = BusinessConstants.HTTP + serverIp + ":8082/file/" + fileUUID;
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
	 * 文件下载接口
	 *
	 * @param fileUUID
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/{fileUUID}")
	public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
		accExecutor.download(response, filePath, fileUUID);
	}

	/**
	 * 文件下载接口   /file/{fileUUID}
	 *
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/downloadTemplate")
	public void download(HttpServletResponse response) throws IOException {
		String classPath = this.getClass().getClassLoader().getResource("templates/").getFile();
		accExecutor.download(response, classPath, templateName);
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

}
