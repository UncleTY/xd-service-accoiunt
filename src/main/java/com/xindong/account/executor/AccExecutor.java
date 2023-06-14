package com.xindong.account.executor;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.io.FileUtil;
import com.xindong.account.common.BusinessConstants;
import com.xindong.account.dto.ResultDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Component
public class AccExecutor {


	@Async
	public void saveFile(List<ResultDTO> result, String filePath, String fileName) throws IOException {
		Workbook workbook = ExcelExportUtil.exportExcel(
				new ExportParams(null, null, BusinessConstants.CHECK_RESULT),
				ResultDTO.class,
				result);
		File saveFile = new File(filePath);
		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(filePath + fileName);
		workbook.write(fos);
		fos.close();
	}

	public void download(HttpServletResponse response, String filePath, String fileName) throws IOException {
		// 根据文件的唯一标识码获取文件
		File uploadFile = new File(filePath + fileName);
		// 设置输出流的格式
		ServletOutputStream os = response.getOutputStream();
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uploadFile.getName(), "UTF-8"));
		response.setContentType("application/octet-stream");
		// 读取文件的字节流
		os.write(FileUtil.readBytes(uploadFile));
		os.flush();
		os.close();
	}
}
