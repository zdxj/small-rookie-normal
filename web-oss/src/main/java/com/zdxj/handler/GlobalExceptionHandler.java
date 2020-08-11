package com.zdxj.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
	 * 实体类字段绑定属性验证错误
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = BindException.class)
	public Result<Object> bindExceptionHandler(BindException e) throws Exception {
	    return Result.failed(e.getBindingResult().getFieldError().getDefaultMessage());
	}
	
	/**
	 * 全部错误
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public Result<Object> exceptionErrorHandler(Exception e) throws Exception {
		logger.error("全局拦截错误:",e);
		return Result.failed("系统错误，请稍后重试");
	}
}
