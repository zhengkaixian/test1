package com.zero.base.util.excelanalysis.enums;

/**
 * @ClassName: CellRule
 * @Description: 数据类型
 * @author:
 * @date: 2018-9-4 下午3:36:35
 *
 */
public enum DataType {
	
	string("string", 1), // 字符校验
	
	intType("intType", 2), // 整形校验
	
	doubleType("doubleType", 3), // 浮点型
	
	date("date", 4), // 时间
	
	dateTime("dateTime", 5), // 日期
	
	expression("expression", 6);// 自定义正则校验

	private String key;
	private int value;

	private DataType(String key, int value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @Title: getCodeValue
	 * @Description: 根据名称获取操作类型的值
	 * @param operateType 操作类型名称
	 * @return
	 * @return String 操作类型值
	 */
	public static int getCodeValue(String operateType) {
		int value = 0;
		for (DataType ope : DataType.values()) {
			if (ope.key.equals(operateType)) {
				value = ope.getValue();
			}
		}
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
