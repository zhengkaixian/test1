package com.zero.base.util.excelanalysis.model;

/**
 * @ClassName: SheetTitle
 * @Description: sheet标题实体类
 * @author: 
 * @date: 2019年5月20日 下午4:30:57
 *
 */

public class SheetTitle {

	/**
	 * @fieldName: rowIndex
	 * @fieldType: int
	 * @Description: 所在行
	 */

	private int rowIndex;

	/**
	 * @fieldName: tilteIndex
	 * @fieldType: int
	 * @Description: 所在列数
	 */

	private int columnIndex;

	/**
	 * @fieldName: titleValue
	 * @fieldType: String
	 * @Description: 标题值
	 */

	private String titleValue;
	

	/**
	 * @param rowIndex
	 * @param columnIndex
	 * @param titleValue
	 */
	public SheetTitle(int rowIndex, int columnIndex, String titleValue) {
		super();
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.titleValue = titleValue;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getTitleValue() {
		return titleValue;
	}

	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	

}
