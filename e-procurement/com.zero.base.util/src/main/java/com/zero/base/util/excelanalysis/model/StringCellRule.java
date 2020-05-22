package com.zero.base.util.excelanalysis.model;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: StringCellRule
 * @Description: 字符成类型校验规则
 * @author:
 * @date: 2018-9-4 下午3:33:29
 *
 */

public class StringCellRule extends CellRule {
	/**
	 * 非法字符校验正则表达式
	 */
	private String illegalExpression = "^(^_*$)|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|AND|OR|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|null)\\b)$";
	// private String
	// illegalExpression="(^_*$)|([\\\\`~!$^&*()=|{}':;'\\[\\].<>/?~！￥……&*（）——|{}#【】《》‘；：”“'。，、？%-+\\(\\)\\\"])|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|AND|OR|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|null)\\b)";
	/**
	 * 最长长度
	 */
	private int maxLength;
	/**
	 * 是否校验非法字符
	 */
	private boolean checkIllegalChar;

	public StringCellRule() {

	}

	public StringCellRule(String cellName, boolean notNull, String mapColumn, String beanFiled, int maxLength,
			boolean checkIllegalChar) {
		super();
		setCellName(cellName);
		setNotNull(notNull);
		setMapColumn(mapColumn);
		setBeanFiled(beanFiled);
		this.maxLength = maxLength;
		this.checkIllegalChar = checkIllegalChar;
	}

	public String getIllegalExpression() {
		return illegalExpression;
	}

	public void setIllegalExpression(String illegalExpression) {
		this.illegalExpression = illegalExpression;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public CheckCellInfo checkData(String value) {
		int length = 0;
		boolean success = true;
		String meg = "";
		if (!StringUtils.isEmpty(value)) {
			length = value.length();
		}
		if (checkIllegalChar && match(value, illegalExpression)) {// 非法字符判断
			success = false;
			meg = getCellName() + "不能输入非法字符";
		} else if (length > maxLength) {// 长度判断
			success = false;
			meg = getCellName() + "最多请输入" + getMaxLength() + "字符";
		} else if (isNotNull() && length == 0) {// 必填判断
			success = false;
			meg = getCellName() + "不能为空";
		}
		CheckCellInfo checkCellInfo = new CheckCellInfo();
		checkCellInfo.setSuccess(success);
		checkCellInfo.setMeg(meg);
		return checkCellInfo;
	}

	public boolean match(String text, String reg) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(reg))
			return false;
		return Pattern.compile(reg).matcher(text).find();
	}
//	public static void main(String[] args) {
//		StringCellRule stringCellRule=new StringCellRule(null, false, null, null, 5,false);
//		String reg="^((0\\.\\.1-0\\.\\.1)|(0\\.\\.1-1)|(0\\.\\.1-0\\.\\.\\*)|(0\\.\\.1-1\\.\\.\\*)|(1-0\\.\\.\\*)|(1-1\\.\\.\\*))$";
//		System.out.println(reg);
//		String te="^\\.\\*$";
//		String illegalExpression="(^_*$)|([\\\\`~!$^&*()=|{}':;'\\[\\].<>/?~！￥……&*（）——|{}#【】《》‘；：”“'。，、？%-+\\(\\)\\\"])|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|AND|OR|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|null)\\b)";
//		System.out.println(te+"t_dm_metada\\\"ta_test_stage");
//		System.out.println(stringCellRule.match("t_dm_metada\\\"ta_test_stage", illegalExpression));
//	}
}
