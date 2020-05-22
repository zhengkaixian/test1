package com.zero.base.util.excelexport.model;


/** 
 * @ClassName: CellRegion2 
 * @Description: TODO (这里用一句话描述这个类的作用)
 * @author: 
 * @date: 2018年12月13日 下午9:03:39
 *
 */

public class CellRegion {

	/**
	 * @fieldName: startRow
	 * @fieldType: int
	 * @Description: 起始行
	 */
	private int startRow;

	/**
	 * @fieldName: startCol
	 * @fieldType: int
	 * @Description: 起始列
	 */
	private int startCol;

	/**
	 * @fieldName: endRow
	 * @fieldType: int
	 * @Description: 终止行
	 */
	private int endRow;

	/**
	 * @fieldName: endCol
	 * @fieldType: int
	 * @Description: 终止列
	 */
	private int endCol;

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	/**
	 * @Title: CellRegion
	 * @Description: 表头合并数据
	 * @param startRow
	 *            开始行位置
	 * @param startCol
	 *            开始列位置
	 * @param endRow
	 *            终止行位置
	 * @param endCol
	 *            终止列位置
	 * @return
	 */
	public CellRegion(int startRow, int startCol, int endRow, int endCol) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = endRow;
		this.endCol = endCol;
	}
}
