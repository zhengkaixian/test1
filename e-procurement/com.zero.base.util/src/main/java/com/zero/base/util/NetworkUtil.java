package com.zero.base.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkUtil {
	private static final Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

	public static boolean isConnected(String ip, int port) {
		boolean isConnected = false;
		if (StringUtil.isEmpty(port)) {
			throw new RuntimeException("port is null!");
		}
		if (StringUtil.isEmpty(ip)) {
			throw new RuntimeException("ip is null!");
		}
		TelnetClient tc = new TelnetClient();
		try {
			tc.connect(ip, port);
			isConnected = true;
		} catch (IOException e) {
			isConnected = false;
		} finally {
			try {
				tc.disconnect();
			} catch (IOException e) {
				isConnected = false;
			}
		}
		return isConnected;
	}
	public static boolean isConnected(String ip, String port) {
		boolean isConnected = false;
		if (StringUtil.isEmpty(port)) {
			throw new RuntimeException("port is null!");
		}
		if (StringUtil.isEmpty(ip)) {
			throw new RuntimeException("ip is null!");
		}
		try {
			isConnected = NetworkUtil.isConnected(ip, Integer.parseInt(port));
		} catch (NumberFormatException e) {
			isConnected = false;
			logger.error("string转int失败,配置错误", e);
		} catch (Exception e) {
			isConnected = false;
			logger.error("链接异常！", e);
		}
		return isConnected;
	}
	
	
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = "";
		ipAddress = request.getHeader("x-forwarded-for");
		
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
			ipAddress = request.getRemoteAddr(); 
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		if (("127.0.0.1").equals(ipAddress) || ("0:0:0:0:0:0:0:1").equals(ipAddress)) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				logger.error("获取用户真实IP地址失败!", e);
			}
			if(inet!=null){
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
//	
//	/**
//	 * 获取用户真实IP地址
//	 * @param request
//	 * @return
//	 */
//	public static String getIpAddress(HttpServletRequest request) {
//		String ipAddress = "";
//		ipAddress = request.getHeader("x-forwarded-for");
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("Proxy-Client-IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("Proxy-Client-IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("HTTP_CLIENT_IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
//		}
//		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getRemoteAddr();
//		}
//		
//		if (("127.0.0.1").equals(ipAddress) || ("0:0:0:0:0:0:0:1").equals(ipAddress)) {
//			// 根据网卡取本机配置的IP
//			InetAddress inet = null;
//			try {
//				inet = InetAddress.getLocalHost();
//			} catch (UnknownHostException e) {
//				logger.error("获取用户真实IP地址失败!", e);
//			}
//			ipAddress = inet.getHostAddress();
//		}
//		
//		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
//			if (ipAddress.indexOf(",") > 0) {
//				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
//			}
//		}
//		return ipAddress;
//	}
}
