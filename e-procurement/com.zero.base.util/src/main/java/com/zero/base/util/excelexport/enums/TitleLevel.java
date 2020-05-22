package com.zero.base.util.excelexport.enums;

/**
 * @ClassName: TitleLevel
 * @Description: 数据类型
 * @author: 
 * @date: 2018-9-4 下午3:36:35
 *
 */
public enum TitleLevel {
	level1("level1", (short) 20), // 一级标题
	level2("level2", (short) 15), // 二级标题
	level3("level3", (short) 10),// 三级标题
	;
	private String key;
	private short value;

	private TitleLevel(String key, short value) {
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
		for (TitleLevel ope : TitleLevel.values()) {
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

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

}
