package com.zdxj.core;

import java.io.Serializable;

import com.zdxj.constant.GlobalConstants;


/**
 * @author zhaodx
 * @date 2019/6/22 17:40
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -950286503617029359L;

    /* 返回结果*/
    private Integer code;

    /* 返回信息*/
    private String msg;

    /* 返回结果数据*/
    private T result;

    public Result () {}
    
    /**
     * 返回成功
     */
    public static <T> Result<T> success() {
    	return restResult(GlobalConstants.SUCCESS,"操作成功",null);
    }
    
    /**
     * 返回成功
     * @param result
     */
    public static <T> Result<T> success(T result) {
    	return restResult(GlobalConstants.SUCCESS,"操作成功",result);
    }
    
    /**
     * 返回成功
     * @param result
     */
    public static <T> Result<T> success(String msg ,T result) {
    	return restResult(GlobalConstants.SUCCESS,msg,result);
    }
    
    /**
     * 返回失败
     */
    public static <T> Result<T> failed() {
    	return restResult(GlobalConstants.FAILURE,"操作失败",null);
    }
    
    /**
     * 返回失败
     */
    public static <T> Result<T> failed(String msg) {
    	return restResult(GlobalConstants.FAILURE,msg,null);
    }
    
    /**
     * 返回失败
     * @param result
     */
    public static <T> Result<T> failed(String msg,T result) {
    	return restResult(GlobalConstants.FAILURE,msg,result);
    }
    
    /**
     * 自定义返回
     * @param result
     */
    public static <T> Result<T> result(Integer code ,String msg,T result) {
    	return restResult(code,msg,result);
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回结果
     *
     * @param code
     * @param msg
     * @param result
     */
    public Result(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    private static <T> Result<T> restResult(Integer code, String msg,T data) {
    	Result<T> apiResult = new Result<T>();
		apiResult.setCode(code);
		apiResult.setResult(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
    
    /**
     * 是否返回成功
     * @author zhaodx
     * @date 2020-08-10 10:42
     * @return
     */
    public boolean isOk() {
    	return GlobalConstants.SUCCESS.equals(this.code);
    }
    
    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
