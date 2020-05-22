package com.zero.base.util.excelexport.model;

import com.zero.base.util.excelexport.enums.TitleLevel;

/**
 * @ClassName: CellTitle
 * @Description: TODO (这里用一句话描述这个类的作用)
 * @author: 
 * @date: 2018年12月12日 下午7:54:09
 *
 */

public class CellTitle {

	/**
	 * @fieldName: rowIndex
	 * @fieldType: int
	 * @Description: 标题行下标
	 */

	private int rowIndex;
	/**
	 * @fieldName: columnIndex
	 * @fieldType: int
	 * @Description: 标题列下标
	 */

	private int columnIndex;

	/**
	 * @fieldName: titleText
	 * @fieldType: String
	 * @Description: 标题名称
	 */

	private String titleText;

	/**
	 * @fieldName: colWidth
	 * @fieldType: Integer
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */

	private Integer colWidth;

	/**
	 * @fieldName: titleLevel
	 * @fieldType: TitleLevel
	 * @Description: 标题等级
	 */

	private TitleLevel titleLevel;

	public CellTitle(int rowIndex, int columnIndex, String titleText, TitleLevel titleLevel, Integer colWidth) {
		super();
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.titleText = titleText;
		this.colWidth = colWidth;
		this.titleLevel = titleLevel;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getColWidth() {
		return colWidth;
	}

	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}

	public TitleLevel getTitleLevel() {
		return titleLevel;
	}

	public void setTitleLevel(TitleLevel titleLevel) {
		this.titleLevel = titleLevel;
	}

}
