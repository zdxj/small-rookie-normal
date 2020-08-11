package com.zdxj.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aliyun.image")
public class AliyunImgProps {

	//OSS的访问密钥
	private String accessKeyId;
	
	//OSS的访问密钥
	private String accessKeySecret;
	
	//Bucket用来管理所存储Object的存储空间
	private String bucketName;
	
	//访问OSS的域名
	private String endpoint;
	
	//文件前缀
	private String bucketUrl ;
	
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getBucketUrl() {
		return bucketUrl;
	}
	public void setBucketUrl(String bucketUrl) {
		this.bucketUrl = bucketUrl;
	}
	
	
}
