package com.zdxj.utils;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.zdxj.core.Result;
import com.zdxj.properties.AliyunImgProps;

@Component
public class AliyunImageUtil {

	@Autowired
	private AliyunImgProps aliyunImgProps;
	
	/**
	 * 上传图片
	 * @param file
	 * @param objectName
	 * @return
	 */
	public Result<Object> putObject(MultipartFile file,String path ,String objectName) {
		OSS ossClient =null;
		try {
			ossClient = new OSSClientBuilder().build(aliyunImgProps.getEndpoint(), aliyunImgProps.getAccessKeyId(), aliyunImgProps.getAccessKeySecret());
			InputStream inputStream = file.getInputStream();
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(inputStream.available());
			objectMetadata.setCacheControl("max-age=315360000");
			objectMetadata.setContentType(getcontentType(objectName.substring(objectName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + objectName);
			ossClient.putObject(aliyunImgProps.getBucketName(), path+objectName, inputStream,objectMetadata);
		}catch(Exception e) {
			e.printStackTrace();
			return Result.failed(e.getMessage());
		}finally {
			if(ossClient != null) {
				ossClient.shutdown();
			}
		}
		String endPath = "/"+path+objectName;
		return Result.success(aliyunImgProps.getEndpoint()+endPath, endPath);
	}
	
	/**
	 * 删除文件
	 * @param objectName
	 * @return
	 */
	public Result<Object> deleteObject(String objectName){
		if(StringUtils.isBlank(objectName)) {
			return Result.failed("文件不存在");
		}
		if(objectName.startsWith("/")) {
			objectName = objectName.substring(1);
		}
		OSS ossClient =null;
		try {
			ossClient = new OSSClientBuilder().build(aliyunImgProps.getEndpoint(), aliyunImgProps.getAccessKeyId(), aliyunImgProps.getAccessKeySecret());
			ossClient.deleteObject(aliyunImgProps.getBucketName(), objectName);
		}catch(Exception e) {
			e.printStackTrace();
			return Result.failed(e.getMessage());
		}finally {
			if(ossClient != null) {
				ossClient.shutdown();
			}
		}
		return Result.success();
	}
	
	
	/**
	 * 获取contentType
	 * @param filenameExtension
	 * @return
	 */
	public static String getcontentType(String filenameExtension) {
		if (filenameExtension.equalsIgnoreCase(".bmp")) {
			return "image/bmp";
		}
		if (filenameExtension.equalsIgnoreCase(".gif")) {
			return "image/gif";
		}
		if (filenameExtension.equalsIgnoreCase(".jpeg") || filenameExtension.equalsIgnoreCase(".jpg")
				|| filenameExtension.equalsIgnoreCase(".png")) {
			return "image/jpeg";
		}
		if (filenameExtension.equalsIgnoreCase(".html")) {
			return "text/html";
		}
		if (filenameExtension.equalsIgnoreCase(".txt")) {
			return "text/plain";
		}
		if (filenameExtension.equalsIgnoreCase(".vsd")) {
			return "application/vnd.visio";
		}
		if (filenameExtension.equalsIgnoreCase(".pptx") || filenameExtension.equalsIgnoreCase(".ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (filenameExtension.equalsIgnoreCase(".docx") || filenameExtension.equalsIgnoreCase(".doc")) {
			return "application/msword";
		}
		if (filenameExtension.equalsIgnoreCase(".xml")) {
			return "text/xml";
		}
		if (filenameExtension.equalsIgnoreCase(".pdf")) {
			return "application/pdf";
		}
		if (filenameExtension.equalsIgnoreCase(".xlsx")) {  
        	return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";  
        } 
		if (filenameExtension.equalsIgnoreCase(".tif") || filenameExtension.equalsIgnoreCase(".tiff")) {  
			return "image/tiff";  
		} 
		return "image/jpeg";
	}
}
