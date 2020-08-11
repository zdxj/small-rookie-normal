package com.zdxj.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.zdxj.core.Result;



/**
 * 系统全局错误捕捉
 * @author zhaodx
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	/**
	 * 全部错误
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public Result<Object> exceptionErrorHandler(Exception e) throws Exception {
		if(e instanceof MaxUploadSizeExceededException) {
			return Result.failed("文件大小不能超过1M");
		}
		logger.error("全局拦截错误:",e);
		return Result.failed("系统错误，请稍后重试");
	}
}
