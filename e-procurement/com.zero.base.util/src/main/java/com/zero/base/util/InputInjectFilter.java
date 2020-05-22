package com.zero.base.util;

import org.springframework.web.util.HtmlUtils;

/**
 * @author Administrator
 * 
 */
public class InputInjectFilter {

	/**
	 * html反转义
	 * 
	 * @param input
	 * @return
	 */
	public static String decodeInputString(String input) {
		String resut = HtmlUtils.htmlUnescape(input);
		return resut;
	}

}
