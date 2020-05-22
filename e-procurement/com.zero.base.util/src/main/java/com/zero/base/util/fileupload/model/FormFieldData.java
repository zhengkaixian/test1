package com.zero.base.util.fileupload.model;

/**
 * @ClassName: FormFieldData
 * @Description: 表单字段数据
 * @author: 
 * @date: 2018年12月7日 下午6:24:29
 *
 */

public class FormFieldData {
	/**
	 * 字段名称
	 */
	String fieldName;
	/**
	 * 字段值
	 */
	String fieldValue;

	/**
	 * 
	* <p>Title:       FormFieldData</p>
	* <p>Description: 构造函数</p>
	* @param fieldName
	* @param fieldValue
	 */
	public FormFieldData(String fieldName, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
