package com.zdxj.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.feignclient.SystemConfigFeignClient;
import com.zdxj.model.SystemConfigEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.util.RedisUtils;

@Component
public class CommonService {

	private Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private SystemConfigFeignClient systemConfigFeignClient ;
	
	/**
	 * 获取系统设置信息
	 * @author zhaodx
	 * @date 2020-08-07 10:35
	 * @return
	 */
	public SystemConfigEntity getSystemConfig() {
		Object object = redisUtils.get(GlobalConstants.SYSTEN_CONFIG_KEY+1);
		if(object != null) {
			return (SystemConfigEntity)object;
		}
		Result<SystemConfigEntity> byId = systemConfigFeignClient.getById(1);
		return byId.getResult();
	}
	
	/**
	 * 获取当前登录用户
	 * @author zhaodx
	 * @date 2020-07-28 15:13
	 * @return
	 */
	public SystemManagerEntity getLoginUser() {
		Subject currentUser = SecurityUtils.getSubject();
		Object object = currentUser.getSession().getAttribute(GlobalConstants.SESSION_MANAGER_KEY);
		if(object instanceof SystemManagerEntity) {
			return (SystemManagerEntity)object;
		}
		return null ;
	}
	
	/**
	 * 验证图片验证码是否正确
	 * @author zhaodx
	 * @date 2020-07-28 17:59
	 * @param verifycode
	 * @return
	 */
	public boolean checkVerifyCode(String verifycode) {
		Session session = SecurityUtils.getSubject().getSession();
		Object tradeVerify = session.getAttribute(GlobalConstants.PICTURE_VERIFICATION_CODE_KEY);
		session.removeAttribute(GlobalConstants.PICTURE_VERIFICATION_CODE_KEY);
		if (tradeVerify != null && verifycode.length() > 0 && tradeVerify.toString().toLowerCase().equals(verifycode.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取用户真实IP地址
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");

		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("X-Real-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();

			if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
				// 根据网卡获取本机配置的IP地址
				InetAddress inetAddress = null;
				try {
					inetAddress = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					logger.error("根据网卡获取本机配置的IP地址失败", e);
				}
				ipAddress = inetAddress==null?"":inetAddress.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
		if (null != ipAddress && ipAddress.length() > 15) {
			// "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
    }
}
