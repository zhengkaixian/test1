package com.zero.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateStr {
	static String COMMON = "[`~!$^&*\"=|{}';'\\[\\].<>/?~！￥……&*——|{}【】#\\\\‘；：”“'。’，、？%+]|(^(n|N)(u|U)(l|L)(l|L)$)";
	
	static String FILE_NAME = "[`~!$^&*\"=|{}';'\\[\\]<>/?~！￥……&*——|{}【】#\\\\‘；：”“'。’，、？%+]|(^(n|N)(u|U)(l|L)(l|L)$)";
	
	public static boolean isStr(String str) {
		// 编译正则表达式
		Pattern pattern = Pattern.compile(COMMON);
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.find();
		return rs;
	}
	
	public static boolean isFileName(String str) {
		// 编译正则表达式
		Pattern pattern = Pattern.compile(FILE_NAME);
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.find();
		return rs;
	}

	public static boolean valiStrLength(String str) {
		if (str.length() > 64 || str.length() < 1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean valiStrLength2(String str) {
		if (str.length() > 255) {
			return true;
		} else {
			return false;
		}
	}

}
