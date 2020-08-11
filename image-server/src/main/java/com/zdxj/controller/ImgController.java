package com.zdxj.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zdxj.core.Result;
import com.zdxj.util.ToolUtils;
import com.zdxj.utils.AliyunImageUtil;

@RestController
@RequestMapping("/image")
public class ImgController {

	@Autowired
	private AliyunImageUtil aliyunImageUtil ;
	
	
	/**
	 * 上传文件
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("upload")
	public @ResponseBody Result<Object> upload(@RequestParam("file")MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
		
		String app = request.getParameter("app");
		String AllowFileType = ".jpg|.jpeg|.gif|.png|.swf|.rar|.zip|.pdf|.xls|.xlsx|.doc|.docx|.ppt|.pptx|.xml";
		for (MultipartFile file : files) {
			String fileName = UUID.randomUUID().toString().replaceAll("-","");
			String fileExtend = FilenameUtils.getExtension(file.getOriginalFilename());
			fileExtend = StringUtils.isBlank(fileExtend)?".jpg":"."+fileExtend;
			if (!FileValidate(fileExtend, AllowFileType)) {
				return Result.failed("上传格式错误");	// error_filetype
			}
			String datadirectory = new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "/";
			switch (app) {
			case "manager":
				return aliyunImageUtil.putObject(file, app+"/"+datadirectory,fileName+fileExtend);
			default:
				return Result.failed("未知的app请求");
			}
		}
		return Result.failed("上传错误，请稍后重试");
	}
	
	/**
	 * 删除文件
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("deleteObject")
	public Result<Object> deleteObject(HttpServletRequest request, HttpServletResponse response){
		String objectName = ToolUtils.checkStr(request.getParameter("objectName"));
		return aliyunImageUtil.deleteObject(objectName);
	}
	
	
	
	
	/**
	 * 检查文件格式
	 * @param fileExt 文件格式
	 * @return
	 */
	private boolean FileValidate(String fileExt, String allowType) {
		boolean FileGood = false;
		String[] Exts = allowType.split("[|]", -1);
		for (String FileType : Exts) {
			if (fileExt.equals(FileType)) {
				FileGood = true;
				break;
			}
		}
		return FileGood;
	}
}
