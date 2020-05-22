package com.zero.base.util.excelexport.enums;

/**
 * @ClassName: TitleLevel
 * @Description: 写入的数据类型
 * @author: 
 * @date: 2018-9-4 下午3:36:35
 *
 */
public enum WriterObjctType {
	array("array", 1), // 数组
	map("map", 2), // map
	bean("bean", 2),// bean对象
	;
	private String key;
	private int value;

	private WriterObjctType(String key, int value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @Title: getCodeValue
	 * @Description: 根据名称获取操作类型的值
	 * @param operateType
	 *            操作类型名称
	 * @return
	 * @return String 操作类型值
	 */
	public static int getCodeValue(String level) {
		int value = 0;
		for (WriterObjctType ope : WriterObjctType.values()) {
			if (ope.key.equals(level)) {
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
