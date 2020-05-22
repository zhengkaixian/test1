package com.zero.base.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;


/**
 * @ClassName: SecurePathUtil
 * @Description: 安全文件路径、文件名判断
 * @author Administrator
 * @date 2016-5-24 上午10:01:57
 * 
 */

public class SecurePathUtil {
	private static final Logger logger = LoggerFactory.getLogger(SecurePathUtil.class);

	private static String[] pathBlackListChars = { "%2e%2e%2f","%2e%2e/","..%2f","%2e%2e%5c","%2e%2e\\","..%5c","%252e%252e%255c","..%255c","../","..\\" };//文件路径黑名单 ，../,..\及它们的变种（encoding和double encoding）

	private static String[] nameBlackListChars = { "%2e%2e%2f","%2e%2e/","..%2f","%2e%2e%5c","%2e%2e\\","..%5c","%252e%252e%255c","..%255c","../","..\\" };//文件名黑名单 --防止出现像“../../tomcat/conf/server.xml”一样的文件名
	

	private static String[] whiteListChars = {"#rootPath#/upload","#rootPath#/uploadFileFromWeb","#rootPath#/tempPath"};//白名单
	
	/**
	 * 
	 * @Title: isSecurePath
	 * @Description: 判断是否是安全的文件路径 --废弃
	 * @param @param filePath
	 * @param @param keyName
	 * @param @param rootPath
	 * @param @return 参数说明
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isSecurePath(String filePath, String keyName, String rootPath) {
		return isSecurePath(filePath, rootPath);
	}
	/**
	 * 
	 * @Title: isSecurePath
	 * @Description: 判断是否是安全的文件路径
	 * @param @param filePath 文件实际全路径(可包含文件名)
	 * @param @param rootPath 默认的父路径
	 * @param @return 参数说明
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isSecurePath(String filePath, String rootPath) {
		String realPath = "";
		filePath =encodeInputString(filePath);
		for(String whiteListChar : whiteListChars){
			if(!StringUtil.isEmpty(whiteListChar)){
				realPath = whiteListChar.replace("#rootPath#", rootPath);
				realPath = encodeInputString(realPath);
				if (!StringUtil.isEmpty(realPath)) {
					if (isSecurePath(realPath) && isSecurePath(filePath)) {//验证是否包含了黑名单中的字符
						File fileA = new File(realPath);
						File fileB = new File(encodeInputString(filePath));
						if (fileA.exists() && fileB.exists()) {// 验证是否在白名单中
							//filePath为文件时
							if(fileB.isFile() && !fileA.getPath().equals(fileB.getParent())){
								return false;
							}
							////filePath为路径时
							if(fileB.isDirectory() && !fileA.getPath().equals(fileB.getPath())){
								return false;
							}
							return true;
						} else {
							return true;
						}
					}else{
						return false;
					}
				} else {
					logger.error("白名单中文件路径为空!");
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @Title: isSecurePath
	 * @Description: 判断是否是安全的文件路径
	 * @param @param filePath
	 * @param @return 参数说明
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isSecurePath(String filePath) {
		return (StringUtils.indexOfAny(filePath, pathBlackListChars) < 0);
	}
	
	/**
	 * 
	 * @Title: isSecureFileName
	 * @Description: 判断是否是安全的文件名        --针对下载文件时攻击者篡改文件名的安全判断
	 * @param @param fileName
	 * @param @return 参数说明
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isSecureFileName(String fileName) {
		return (StringUtils.indexOfAny(fileName, nameBlackListChars) < 0);
	}
	/**
	 * 过滤html标签
	 * 
	 * @param input
	 * @return
	 */
	public static String encodeInputString(String input) {
	// resut = ESAPI.encoder().canonicalize(input);
	String resut = HtmlUtils.htmlEscape(input);
	return resut;

}
}
